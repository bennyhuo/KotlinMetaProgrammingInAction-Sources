package com.bennyhuo.kotlin.meta

import com.squareup.javapoet.ArrayTypeName
import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.MethodSpec
import com.squareup.javapoet.ParameterSpec
import com.squareup.javapoet.ParameterizedTypeName
import com.squareup.javapoet.TypeName
import com.squareup.javapoet.TypeSpec
import com.squareup.javapoet.TypeVariableName
import com.squareup.javapoet.WildcardTypeName
import java.io.File
import javax.lang.model.element.Modifier

/**
 * Created by benny.
 */
fun generateHelloWorld() {
  // main 方法的定义
  val mainMethod: MethodSpec = MethodSpec.methodBuilder("main")
    // 添加 public static 关键字
    .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
    // 添加参数
    .addParameter(
      // 参数的定义
      ParameterSpec.builder(
        // 参数的类型为 String[]，Kotlin 当中对应为 Array<String>
        Array<String>::class.java, "args"
      ).build()
    )
    // 返回 void
    .returns(TypeName.VOID)
    // 方法体，$T 是 JavaPoet 当中的类型格式化符，对应于后面的 System::class.java
    .addStatement("\$T.out.println(\"Hello, World\")", System::class.java)
    .addStatement(
      "\$T.out.println(\"Hello, World\", \$N, \$N)",
      System::class.java,
      "name",
      "name"
    )
    .build()

  // HelloWorld 类的定义
  val helloWorldType: TypeSpec = TypeSpec.classBuilder("HelloWorld")
    // 添加 public 关键字
    .addModifiers(Modifier.PUBLIC)
    // 添加 main 方法
    .addMethod(mainMethod).build()

  JavaFile.builder("com.bennyhuo.java.helloworld", helloWorldType)
    // 为生成的文件添加注释
    .addFileComment("This is generated by JavaPoet.")
    // 处理自动导包时，忽略 java.lang 包下类的导入
    .skipJavaLangImports(true)
    .build()
    // 写入到 data 目录下
    .writeTo(File("data"))
}

fun className() {
  val className = ClassName.get(
    "com.bennyhuo.kotlin.meta",
    "StateManager",
    "OnStateChangedListener"
  )
  println(className.reflectionName())
  println(className.simpleNames())

  val className2 = ClassName.get("com.bennyhuo.kotlin.meta", "StateManager")
  println(className2)
}

fun arrayTypeName() {
  // 构造 Java 类型 User
  val userClassName = ClassName.get("com.bennyhuo.kotlin.meta", "User")
  // 构造 Java 类型 User[]
  val userArrayTypeName = ArrayTypeName.of(userClassName)
  println(userArrayTypeName)

  // 如果类型在元程序当中可见，也可以直接用 Class 创建
  val stringArrayTypeName = ArrayTypeName.of(String::class.java)
  println(stringArrayTypeName)
}

fun parameterizedTypeName() {
  val userClassName = ClassName.get("com.bennyhuo.kotlin.meta", "User")

  ParameterizedTypeName.get(List::class.java, String::class.java).nestedClass(
    "test", listOf(
      userClassName
    )
  ).let {
    println(it)
  }

  println(
    ParameterizedTypeName.get(
      ClassName.get(Set::class.java),
      userClassName
    )
  )

  println(TypeVariableName.get("T", userClassName))

}

fun typeVariableName() {
  val userTypeVar = TypeVariableName.get("User")
  val tTypeVar = TypeVariableName.get("T", ClassName.get(Number::class.java))

  val subOfInt =
    WildcardTypeName.subtypeOf(ClassName.bestGuess("com.bennyhuo.kotlin.deepcopy.DeepCopyable"))
  println(subOfInt)

  val superOfString = WildcardTypeName.supertypeOf(Number::class.java)
  println(superOfString)

  TypeSpec.classBuilder("Test").addTypeVariable(userTypeVar)
    .addTypeVariable(tTypeVar)
    .build().let(::println)
}

fun codeBlock() {
  val text = "Hello World"
//  val codeBlock = CodeBlock.of("println(\"$text\")")
  val codeBlock = CodeBlock.of("println(\$L)", text)
  println(codeBlock)
}

fun formatType() {
  // User 类型
  val userClassName = ClassName.get(
    "com.bennyhuo.kotlin.meta.data",
    "User"
  )

  println(CodeBlock.of("System.out.println(\$L())", userClassName))
  println(CodeBlock.of("val user = \$T()", userClassName))

  val codeBlock = CodeBlock.of("System.out.println(\$T())", userClassName)
  // 例如 text 的值为 Hello World
  val text = "Hello World"
  // System.out.println(
  // "Hello World"
  // )
  val codeBlock2 = CodeBlock.of("System.out.println(\n\$S\n)", text)
  println(codeBlock2)

  val text2 = "Source file generation can be useful when doing things " +
    "such as annotation processing or interacting with metadata files."
  //  System.out.println(
  //    "Source file generation can be useful when doing things such as annotation processing or interacting with metadata files.")
  val codeBlock3 = CodeBlock.of("System.out.println(\$Z\$S)", text2)

  println(codeBlock3)
  val codeBlock4 = CodeBlock.of("this.\$1N = other.\$1N", "name")
  println(codeBlock4)

  val map = mapOf(
    "name" to "hello",
    "value" to "Meta Programming"
  )
  val codeBlock5 = CodeBlock.builder().addNamed(
    "String \$name:N = \$value:S", map
  ).build()
  println(codeBlock5)

  //  if (a == b) {
  //    <if branch>;
  //  } else {
  //    <else branch>;
  //  }
  CodeBlock.builder()
    .beginControlFlow("if (\$N == \$N)", "a", "b")
    .addStatement("<if branch>")
    .nextControlFlow("else")
    .addStatement("<else branch>")
    .endControlFlow()
    .build().let { println(it) }


//  do {
//    <loop>;
//  } while(condition);
  CodeBlock.builder()
    .beginControlFlow("do")
    .addStatement("<loop>")
    .endControlFlow("while(\$N)", "condition")
    .build().let { println(it) }

  JavaFile.builder(
    "com.bennyhuo.kotlin.meta",
    TypeSpec.classBuilder("HelloWorld")
      .addMethod(
        MethodSpec.methodBuilder("sayHi")
          .addStatement(codeBlock)
          .addStatement(codeBlock2)
          .build()
      ).build()
  ).build().writeTo(System.out)
}

fun main() {
//  className()
//  arrayTypeName()
//  parameterizedTypeName()
//  typeVariableName()
//
//  codeBlock()
  formatType()
}