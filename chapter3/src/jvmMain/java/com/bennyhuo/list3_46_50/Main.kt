package com.bennyhuo.list3_46_50

fun main() {
  // 3-46
  val map: Map<String, String> = mapOf(
    "x" to "Hello",
    "y" to "World"
  )

  val x by map
  val y by map

  println(x)
  println(y)
}