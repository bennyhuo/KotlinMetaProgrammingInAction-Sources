package com.bennyhuo.list2_10

import java.io.File

fun unsafeFileOperations() {
  // 2-10
  File("illegal path").list().forEach {
    println(it)
  }
}

fun safeFileOperations() {
  // 2-11
  File("illegal path").list()?.forEach {
    println(it)
  }
}

fun main() {
  safeFileOperations()
  runCatching {
    unsafeFileOperations()
  }
}
