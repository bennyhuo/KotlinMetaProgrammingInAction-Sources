package com.bennyhuo.list4_52_58

import com.squareup.kotlinpoet.buildCodeBlock

fun main() {
  // 4-58
  val cb = buildCodeBlock {
    addStatement("val status = getStatus()")
    beginControlFlow("while (status == 0)")
    addStatement("...")
    endControlFlow()
  }
  println(cb)
}