package com.bennyhuo.kotlin.ksp

import com.google.devtools.ksp.symbol.KSDeclaration
import com.google.devtools.ksp.symbol.KSTypeReference

/**
 * Created by benny.
 */
fun KSDeclaration.modifiersToString(): String {
  return if (modifiers.isEmpty()) {
    ""
  } else {
    modifiers.joinToString(" ", postfix = " ") { it.name.lowercase() }
  }
}

fun KSTypeReference.qualifiedName(): String? {
  return resolve().declaration.qualifiedName?.asString()
}