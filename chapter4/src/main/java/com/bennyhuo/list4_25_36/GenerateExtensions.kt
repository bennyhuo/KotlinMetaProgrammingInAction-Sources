package com.bennyhuo.list4_25_36

import org.jetbrains.android.anko.ClassProcessor
import org.jetbrains.android.anko.artifact.Artifact
import org.jetbrains.android.anko.isStatic
import org.jetbrains.android.anko.isSynthetic
import org.jetbrains.android.anko.toKMethod
import org.jetbrains.android.anko.utils.ImportList
import org.jetbrains.android.anko.utils.KMethod
import org.jetbrains.android.anko.utils.MethodNodeWithClass
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
import kotlin.reflect.KProperty1
import kotlin.reflect.full.declaredMemberProperties

/**
 * Created by benny.
 */

const val GENERATE_PATH = "generated/src/main/java"

fun <T : Any> T.asMap(): Map<String, Any?> {
  return this::class.declaredMemberProperties.associate { property ->
    property as KProperty1<T, *>
    property.name to property.get(this)?.let { value ->
      when (value) {
        is String -> value
        is Iterable<*> -> value.map { it?.asMap() }
        else -> value.asMap()
      }
    }
  }
}

//region 4-25
data class FunctionInfo(
  val receiverType: String,
  val functionName: String,
  val args: String,
  val argNames: String,
  // 4-71
  val typeArgs: String = "",
  val whereBlock: String = ""
)

data class ClassInfo(
  val packageName: String,
  val className: String,
  val functions: List<FunctionInfo>
)
//endregion

fun useTemplate() {
  // 4-26
  val template = JtwigTemplate.classpathTemplate("template/extensions.twig")
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
        parameters.joinToString { "${it.name}: ${it.type}" },
        parameters.joinToString { it.name },
      )
    }?.let { functions: List<FunctionInfo> ->
      // 构造模板参数
      val classInfo = ClassInfo(
        classNode.packageName,
        classNode.simpleName,
        functions
      )

      // 渲染模板
      template.render(
        JtwigModel.newModel(classInfo.asMap()),
        FileOutputStream(
          GENERATE_PATH +
            "/${classNode.packageName.replace('.', '/')}" +
            "/${classNode.simpleName}.kt"
        )
      )
    }
  }
}

fun useTemplateWithoutTrailingComma() {
  val template =
    JtwigTemplate.classpathTemplate("template/extensions_trailingcomma.twig")
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

      // 4-31
      FunctionInfo(
        kMethod.parameters.first().type.toString(),
        kMethod.name,
        parameters.joinToString { "${it.name}: ${it.type}" },
        // 注意这里的参数拼接
        parameters.joinToString(separator = "") { ", ${it.name}" },
      )
    }?.let { functions: List<FunctionInfo> ->
      // 构造模板参数
      val classInfo = ClassInfo(
        classNode.packageName,
        classNode.simpleName,
        functions
      )

      // 渲染模板
      template.render(
        JtwigModel.newModel(classInfo.asMap()),
        FileOutputStream("$GENERATE_PATH/${classNode.packageName.replace('.', '/')}/${classNode.simpleName}.kt")
      )
    }
  }
}


fun useTemplateAutoImport() {
  // 4-34
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
    "template/extensions_autoimport.twig",
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

      // 4-35
      FunctionInfo(
        kMethod.parameters.first().type.toString(),
        kMethod.name,
        // 注意这里的 importList
        parameters.joinToString { "${it.name}: ${importList[it.type]}" },
        parameters.joinToString(separator = "") { ", ${it.name}" },
      )
    }?.let { functions: List<FunctionInfo> ->
      // 构造模板参数
      val classInfo = ClassInfo(
        classNode.packageName,
        classNode.simpleName,
        functions
      )

      // 4-36
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

fun main() {
  useTemplateAutoImport()
}