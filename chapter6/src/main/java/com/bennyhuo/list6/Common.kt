package com.bennyhuo.kotlin.meta.ast

import java.io.File

/**
 * Created by benny.
 */
data class DataClassInfo(
  val file: File,
  val line: Int,
  val className: String
)