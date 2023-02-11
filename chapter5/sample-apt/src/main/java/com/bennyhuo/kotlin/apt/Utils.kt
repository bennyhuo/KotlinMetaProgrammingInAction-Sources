package com.bennyhuo.kotlin.apt

import javax.lang.model.element.Element

/**
 * Created by benny.
 */
fun Element.modifiersToString(): String {
  return if (modifiers.isEmpty()) {
    ""
  } else {
    modifiers.joinToString(" ", postfix = " ") { it.name.lowercase() }
  }}

