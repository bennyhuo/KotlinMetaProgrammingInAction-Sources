package com.bennyhuo.list4_39_49

import com.squareup.javapoet.CodeBlock

fun main() {
  // 4-47
  val cb = CodeBlock.builder()
    .beginControlFlow("if (\$N == \$N)", "a", "b")
    .addStatement("<if branch>")
    .nextControlFlow("else")
    .addStatement("<else branch>")
    .endControlFlow()
    .build()

  println(cb)

  // 4-49
  val cb2 = CodeBlock.builder()
    .beginControlFlow("do")
    .addStatement("<loop>")
    .endControlFlow("while(\$N)", "condition")
    .build()
  println(cb2)

}