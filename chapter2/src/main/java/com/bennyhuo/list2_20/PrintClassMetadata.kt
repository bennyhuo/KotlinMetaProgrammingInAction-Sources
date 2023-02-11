package com.bennyhuo.list2_20

import com.bennyhuo.lib.Service
import kotlinx.metadata.jvm.KotlinClassHeader
import kotlinx.metadata.jvm.KotlinClassMetadata
import org.jetbrains.kotlin.kotlinp.ClassPrinter
import org.jetbrains.kotlin.kotlinp.FileFacadePrinter
import org.jetbrains.kotlin.kotlinp.KotlinpSettings
import org.jetbrains.kotlin.kotlinp.LambdaPrinter
import org.jetbrains.kotlin.kotlinp.MultiFileClassFacadePrinter
import org.jetbrains.kotlin.kotlinp.MultiFileClassPartPrinter

private val settings = KotlinpSettings(true)

fun main() {
  Service::class.java.annotations.filterIsInstance<Metadata>()
    .single()
    .let { metadata ->
      val header = KotlinClassHeader(
        metadata.kind,
        metadata.metadataVersion,
        metadata.data1,
        metadata.data2,
        metadata.extraString,
        metadata.packageName,
        metadata.extraInt
      )

      when (val classMetadata = KotlinClassMetadata.read(header)) {
        is KotlinClassMetadata.Class -> {
          ClassPrinter(settings).print(classMetadata)
        }
        is KotlinClassMetadata.FileFacade -> {
          FileFacadePrinter(settings).print(classMetadata)
        }
        is KotlinClassMetadata.SyntheticClass -> {
          LambdaPrinter(settings).print(classMetadata)
        }
        is KotlinClassMetadata.MultiFileClassFacade -> {
          MultiFileClassFacadePrinter().print(classMetadata)
        }
        is KotlinClassMetadata.MultiFileClassPart -> {
          MultiFileClassPartPrinter(settings).print(classMetadata)
        }
        else -> return
      }
    }.let {
      println(it)
    }
}
