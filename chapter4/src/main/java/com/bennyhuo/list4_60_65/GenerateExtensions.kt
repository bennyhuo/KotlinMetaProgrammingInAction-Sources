package com.bennyhuo.list4_60_65

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.TypeVariableName
import org.jetbrains.android.anko.ClassProcessor
import org.jetbrains.android.anko.artifact.Artifact
import org.jetbrains.android.anko.isStatic
import org.jetbrains.android.anko.isSynthetic
import org.jetbrains.android.anko.toKMethod
import org.jetbrains.android.anko.utils.GenericType
import org.jetbrains.android.anko.utils.KMethod
import org.jetbrains.android.anko.utils.KType
import org.jetbrains.android.anko.utils.MethodNodeWithClass
import org.jetbrains.android.anko.utils.genericTypeToKType
import org.jetbrains.android.anko.utils.packageName
import org.jetbrains.android.anko.utils.simpleName
import org.objectweb.asm.tree.ClassNode
import java.io.File

const val GENERATE_PATH = "generated/src/main/java"

// 4-63
fun ClassNode.asClassName() = ClassName(packageName, simpleName)

// 4-60
fun KType.asTypeName(): TypeName {
  return if (isTypeVariable) {
    // 如果 KType 表示的是泛型形参，例如 T
    // 为了逻辑完整，我们通过检查型变参数来构建 TypeVariableName
    when (variance) {
      KType.Variance.COVARIANT -> TypeVariableName(
        fqName,
        variance = KModifier.OUT
      )
      KType.Variance.CONTRAVARIANT -> TypeVariableName(
        fqName,
        variance = KModifier.IN
      )
      // 实际上，KType 从 ASM 读取的 Java 字节码解析而来，Java 不支持声明处型变
      // 因此 variance 的值只会是 INVARIANT，即落入 else 分支
      else -> TypeVariableName(fqName)
    }
  } else {
    // KType 表示的是实际的类型
    val className = ClassName.bestGuess(fqName)
    var typeName: TypeName = className
    if (arguments.isNotEmpty()) {
      // 如果有泛型参数
      typeName = className.parameterizedBy(
        arguments.map { argument: KType ->
          argument.asTypeName()
        }
      )
    }

    // 处理型变的情况，Java 支持使用处型变，因此这个判断是有实际意义的
    when (variance) {
      // 协变，Java 当中形如 ? extends Number
      KType.Variance.COVARIANT -> TypeVariableName(
        "",
        typeName,
        variance = KModifier.OUT
      )
      // 逆变，Java 当中形如 ? super Number
      KType.Variance.CONTRAVARIANT -> TypeVariableName(
        "",
        typeName,
        variance = KModifier.IN
      )
      else -> typeName
    }
  }.copy(isNullable)
}

fun main() {
  // 4-64
  ClassProcessor(
    // Artifact 可以包含多个 jar
    Artifact("utils", listOf(File("data/utils.jar")))
  ).genClassTree().forEach { classNode: ClassNode ->
    // ClassNode 是 asm tree api 当中类的表示
    classNode.methods?.filter {
      // 只保留静态非合成的方法
      it.isStatic && !it.isSynthetic
    }?.map {
      // toKMethod 当中会解析方法的 desc/signature 以还原参数和返回值类型的
      MethodNodeWithClass(classNode, it).toKMethod()
    }?.filter {
      // 由于需要 receiver，参数至少有一个
      it.parameters.isNotEmpty()
    }?.map { kMethod: KMethod ->
      // 4-64
      // 第一个参数作为 receiver，其余的参数作为新生成的函数的参数
      val parameters =
        kMethod.parameters.takeLast(kMethod.parameters.size - 1)

      // 扩展方法的 receiver 类型，也是原方法的第一个参数的类型
      val receiverType: KType = kMethod.parameters.first().type
      // 构造扩展方法的 FunSpec
      FunSpec.builder(kMethod.name)
        .receiver(receiverType.asTypeName())
        .addTypeVariables(
          // 将 KMethod 的泛型参数列表添加到扩展方法当中
          kMethod.typeParameters.map {
            TypeVariableName(it.name,
              it.upperBounds.map { genericType: GenericType ->
                // GenericType 描述的是泛型类型，将其转换为 KType 之后再转为 TypeName
                genericTypeToKType(genericType).asTypeName()
              }
            )
          }
        )
        // 添加参数
        .addParameters(parameters.map {
          ParameterSpec(it.name, it.type.asTypeName())
        })
        // 添加方法体
        .addStatement(
          "return %T.%L(this%L)",
          classNode.asClassName(),
          kMethod.name,
          parameters.joinToString(separator = "") { ", ${it.name}" },
        ).build()
    }?.let { functions: List<FunSpec> ->
      // 4-65
      // 生成文件
      FileSpec.builder(classNode.packageName, classNode.simpleName)
        .also { fileSpec ->
          functions.forEach(fileSpec::addFunction)
        }
        // 调用该函数可以不再导入 kotlin.*、kotlin.collections.* 这样的包
        // 这是 Kotlin 默认导入的包，一般情况无需导入
        .addKotlinDefaultImports()
        .build()
        .writeTo(File(GENERATE_PATH))
    }
  }
}