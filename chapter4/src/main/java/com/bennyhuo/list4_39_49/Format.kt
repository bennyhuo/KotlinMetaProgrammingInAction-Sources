package com.bennyhuo.list4_39_49

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.CodeBlock

fun main() {
  // 4-44
  val user = ClassName.get("com.bennyhuo","User")
  val cb = CodeBlock.of("val user = \$T()", user)
  println(user)
  println(cb)

  // 4-45
  // this.name = other.name
  val cb2 = CodeBlock.of("this.\$1N = other.\$1N", "name")
  println(cb2)

  // 4-46
  val map = mapOf(
    "name" to "hello",
    "value" to "Meta Programming"
  )
  // String hello = "Meta Programming"
  val cb3 = CodeBlock.builder().addNamed(
    "String \$name:N = \$value:S", map
  ).build()
  println(cb3)


}