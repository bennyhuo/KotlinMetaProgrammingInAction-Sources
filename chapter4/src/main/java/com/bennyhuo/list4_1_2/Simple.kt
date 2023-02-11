package com.bennyhuo.list4_1_2

import kotlinx.cli.ArgParser
import kotlinx.cli.ArgType
import kotlinx.cli.default
import kotlinx.cli.required
import java.io.File

// 4-1, 4-2
fun main(args: Array<String>) {
  val parser = ArgParser("MetaSample")
  val outputDir by parser.option(
    ArgType.String, shortName = "o", description = "Output Directory"
  ).required()
  val fileName by parser.option(ArgType.String, shortName = "f", description = "Output FileName").required()
  val packageName by parser.option(ArgType.String, shortName = "P", description = "Package Name").default("main")
  val functionName by parser.option(ArgType.String, shortName = "F", description = "Function Name").default("main")
  val content by parser.option(ArgType.String, shortName = "C", description = "Content").default("HelloWorld")
  parser.parse(args)

  val template = """
    package $packageName
    
    fun ${functionName}() {
      println("$content")  
    }
  """.trimIndent()

  File(outputDir).mkdirs()
  File(outputDir, fileName).writeText(template)
}