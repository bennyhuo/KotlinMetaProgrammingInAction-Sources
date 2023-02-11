package com.bennyhuo.kotlin.utils

import org.junit.jupiter.api.Test

class UtilsTest {

  @Test
  fun test() {
    // 4-20
    val list = listOf("Java", "Kotlin", "C++", "Python")
    println(StringUtils.join(list, ","))
  }

}