package com.bennyhuo.list3_12

import kotlinx.metadata.Flag
import kotlinx.metadata.jvm.KotlinClassHeader
import kotlinx.metadata.jvm.KotlinClassMetadata

// 3-12
data class Location(val lat: Double, val lng: Double)

fun main() {
  // 3-12
  Location::class.java.methods.forEach {
    println(it.name)
  }

  // 3-13
  val metadata = Location::class.java.getAnnotation(Metadata::class.java).let {
    KotlinClassMetadata.read(KotlinClassHeader(
      it.kind,
      it.metadataVersion,
      it.data1,
      it.data2,
      it.extraString,
      it.packageName,
      it.extraInt
    )) as? KotlinClassMetadata.Class
  }
  val isData = metadata?.toKmClass()?.flags?.let {
    Flag.Class.IS_DATA(it)
  } == true

  println(isData)
}