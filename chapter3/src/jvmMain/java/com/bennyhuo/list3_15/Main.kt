package com.bennyhuo.list3_15

import com.bennyhuo.list3_12.Location
import kotlin.reflect.full.functions

fun main() {
  Location::class.functions.forEach {
    println(it.name)
  }
}