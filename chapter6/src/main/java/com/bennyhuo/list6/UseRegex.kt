package com.bennyhuo.kotlin.meta.ast

import java.io.File

// 6-11
val dataClassPattern =
  Regex("""[^\n\s]*((@\w+)\s+)?(\w+\s+)*data\s+(\w+\s+)*class\s+(\w+).*""")

private fun scanForDataClasses(file: File): Sequence<DataClassInfo> {
  val text = file.readText()
  val lineBreaks = sequence {
    text.forEachIndexed { index, char ->
      if (char == '\n') {
        yield(index)
      }
    }
  }
  return dataClassPattern.findAll(text).map { result ->
    val line = lineBreaks.indexOfFirst { it > result.range.first } + 1
    DataClassInfo(file, line, result.groups[5]!!.value)
  }
}

fun main() {
  val sourceRoot = File("chapter6/data")
  sourceRoot.walkTopDown().filter {
    it.isFile && it.name.endsWith(".kt")
  }.forEach { file ->
    scanForDataClasses(file).forEach {
      println("(${it.file}:${it.line}) data class ${it.className}")
    }
  }
}