package com.bennyhuo.list2_26

import com.bennyhuo.lib.Service
import kotlin.reflect.full.companionObjectInstance

fun main() {
  println(Service::class.companionObjectInstance == Service)
}