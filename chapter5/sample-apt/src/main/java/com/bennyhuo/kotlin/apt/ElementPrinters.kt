package com.bennyhuo.kotlin.apt

import java.io.Writer
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement

/**
 * Created by benny.
 */
fun TypeElement.print(writer: Writer) {
  writer.appendLine(
    "${modifiersToString()}class $qualifiedName"
  )

  enclosedElements.forEach {
    if (Modifier.PUBLIC in it.modifiers) {
      val indent = "  "
      when (it) {
        is ExecutableElement -> it.print(writer, indent)
        is VariableElement -> it.print(writer, indent)
      }
    }
  }
}

fun ExecutableElement.print(writer: Writer, indent: String = "  ") {
  if (simpleName.contentEquals("<init>")) {
    // constructor
    writer.appendLine("${indent}${modifiersToString()}$this")
  } else if (simpleName.contentEquals("<clinit>") || simpleName.isBlank()) {
    // static initializer / anonymous
  } else {
    writer.appendLine("${indent}${modifiersToString()}${returnType} $this")
  }
}

fun VariableElement.print(writer: Writer, indent: String = "  ") {
  writer.appendLine("${indent}${modifiersToString()}${asType()} $this")
}