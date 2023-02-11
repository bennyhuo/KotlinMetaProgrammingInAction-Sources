package com.bennyhuo.list4_39_49

import com.squareup.javapoet.ClassName
import com.squareup.javapoet.ParameterizedTypeName

fun main() {
  // 4-42
  val className = ClassName.get(
    // 包名
    "com.bennyhuo.kotlin.meta",
    // 外部类名
    "StateManager",
    // 类名
    "OnStateChangedListener"
  )

  println(className)

  // 4-43
  // List<String>
  println(ParameterizedTypeName.get(List::class.java, String::class.java))
  // User
  val userClassName = ClassName.get("com.bennyhuo.kotlin.meta", "User")
  println(userClassName)
  // Set<User>
  println(ParameterizedTypeName.get(ClassName.get(Set::class.java), userClassName))
}