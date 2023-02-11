package com.bennyhuo.kotlin.ksp

import com.google.devtools.ksp.isConstructor
import com.google.devtools.ksp.isPublic
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSPropertyDeclaration
import java.io.Writer

/**
 * Created by benny.
 */
fun KSClassDeclaration.print(writer: Writer) {
  writer.appendLine(
    "${modifiersToString()}class ${qualifiedName?.asString()}"
  )

  declarations.filter { it.isPublic() }.forEach {
    val indent = "  "
    when (it) {
      is KSFunctionDeclaration -> it.print(writer, indent)
      is KSPropertyDeclaration -> it.print(writer, indent)
    }
  }
}

fun KSFunctionDeclaration.print(writer: Writer, indent: String) {
  writer.append("${indent}${modifiersToString()}fun ")

  this.extensionReceiver?.let {
    writer.append("${it.qualifiedName()}.")
  }

  if (isConstructor()) {
    writer.append("constructor")
  } else {
    writer.append(simpleName.asString())
  }

  writer.append(parameters.joinToString(prefix = "(", postfix = ")") {
    "${it.type.qualifiedName()}"
  })

  if (!isConstructor()) {
    returnType?.let {
      writer.append(": ${it.qualifiedName()}")
    }
  }

  writer.appendLine()
}

fun KSPropertyDeclaration.print(writer: Writer, indent: String) {
  writer.append("${indent}${modifiersToString()}")
  if (isMutable) {
    writer.append("var ")
  } else {
    writer.append("val ")
  }

  extensionReceiver?.let {
    writer.append("${it.qualifiedName()}.")
  }

  writer.append(simpleName.asString())
  writer.append(": ${type.qualifiedName()}")

  writer.appendLine()
}