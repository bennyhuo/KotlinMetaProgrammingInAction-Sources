package com.bennyhuo.list2_29

import kotlinx.metadata.jvm.KotlinModuleMetadata
import org.jetbrains.kotlin.kotlinp.KotlinpSettings
import org.jetbrains.kotlin.kotlinp.ModuleFilePrinter
import java.io.File

val settings = KotlinpSettings(true)

fun main() {
  // 2-29
  KotlinModuleMetadata.read(
    File("chapter2/library/build/classes/kotlin/main/META-INF/library.kotlin_module").readBytes()
  )?.let {
    println(ModuleFilePrinter(settings).print(it))
  }
}