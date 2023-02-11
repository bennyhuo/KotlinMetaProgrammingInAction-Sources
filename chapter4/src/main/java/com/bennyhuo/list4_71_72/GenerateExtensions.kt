package com.bennyhuo.list4_71_72

import com.bennyhuo.list4_25_36.ClassInfo
import com.bennyhuo.list4_25_36.FunctionInfo
import com.bennyhuo.list4_25_36.GENERATE_PATH
import com.bennyhuo.list4_25_36.asMap
import org.jetbrains.android.anko.ClassProcessor
import org.jetbrains.android.anko.artifact.Artifact
import org.jetbrains.android.anko.isStatic
import org.jetbrains.android.anko.isSynthetic
import org.jetbrains.android.anko.toKMethod
import org.jetbrains.android.anko.utils.ImportList
import org.jetbrains.android.anko.utils.KMethod
import org.jetbrains.android.anko.utils.MethodNodeWithClass
import org.jetbrains.android.anko.utils.genericTypeToKType
import org.jetbrains.android.anko.utils.packageName
import org.jetbrains.android.anko.utils.simpleName
import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate
import org.jtwig.environment.EnvironmentConfigurationBuilder
import org.jtwig.functions.FunctionRequest
import org.jtwig.functions.SimpleJtwigFunction
import org.objectweb.asm.tree.ClassNode
import java.io.File
import java.io.FileOutputStream

// 4-71
data class FunctionInfo(
  val receiverType: String,
  val functionName: String,
  val args: String,
  val argNames: String,
  val typeArgs: String = "", // 新增
  val whereBlock: String = "" // 新增
)

fun main() {
  val importList = ImportList()
  val configuration = EnvironmentConfigurationBuilder.configuration()
  configuration.functions().add(object : SimpleJtwigFunction() {
    override fun name() = "imported"

    override fun execute(request: FunctionRequest): Any {
      request.minimumNumberOfArguments(1).maximumNumberOfArguments(1)
      return importList[request.arguments[0].toString()]
    }
  })
  val template = JtwigTemplate.classpathTemplate(
    "template/extensions_type_parameters.twig",
    configuration.build()
  )
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
      // 第一个参数作为 receiver，其余的参数作为新生成的函数的参数
      val parameters =
        kMethod.parameters.takeLast(kMethod.parameters.size - 1)

      FunctionInfo(
        kMethod.parameters.first().type.toString(),
        kMethod.name,
        parameters.joinToString { "${it.name}: ${importList[it.type]}" },
        parameters.joinToString(separator = "") { ", ${it.name}" },
        // 4-72
        // 泛型参数为空时传入""，否则将参数拼接成：" <T1, T2>"，注意前面的空格
        typeArgs = kMethod.typeParameters.takeIf {
          it.isNotEmpty()
        }?.joinToString(
          // 结合模板的配置，后面是不需要空格的，前面需要
          prefix = " <", postfix = ">"
        ) { it.name } ?: "",
        // 泛型参数不为空时，形如 " where T1: Number, T2: Number"
        whereBlock = kMethod.typeParameters.filter {
          // 无泛型约束，不会出现在 where 语句中
          it.upperBounds.isNotEmpty()
        }.flatMap { typeParameter ->
          typeParameter.upperBounds.map {
            // 形如 T: Number
            "${typeParameter.name}: ${genericTypeToKType(it)}"
          }
        }.takeIf { it.isNotEmpty() }
          ?.joinToString(prefix = " where ") ?: ""
      )
    }?.let { functions: List<FunctionInfo> ->
      // 构造模板参数
      val classInfo = ClassInfo(
        classNode.packageName,
        classNode.simpleName,
        functions
      )

      // 渲染模板
      val extensions = template.render(JtwigModel.newModel(classInfo.asMap()))
      FileOutputStream(
        GENERATE_PATH +
          "/${classNode.packageName.replace('.', '/')}" +
          "/${classNode.simpleName}.kt"
      ).bufferedWriter().use {
        it.write("package ${classNode.packageName}\n\n")
        it.write(importList.toString())
        it.write(extensions)
      }
      importList.clear()

    }
  }
}
