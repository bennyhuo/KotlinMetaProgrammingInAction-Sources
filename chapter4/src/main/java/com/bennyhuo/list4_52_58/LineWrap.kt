package com.bennyhuo.list4_52_58

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec

fun main() {
  // 4-56
  FileSpec.builder("com.bennyhuo.kotlin.meta", "a")
    .addFunction(
      FunSpec.builder("wrapLine")
        .addStatement(
          ("val text = \"KotlinPoet is a Kotlin and Java API for generating" +
            " .kt source files. Source file generation can be useful when " +
            "doing things such as annotation processing or interacting with " +
            "metadata files (e.g., database schemas, protocol formats). By " +
            "generating code, you eliminate the need to write boilerplate " +
            "while also keeping a single source of truth for the metadata.\"").replace(' ', '·')
        ).build()
    )
    .build()
    .let { println(it) }
}