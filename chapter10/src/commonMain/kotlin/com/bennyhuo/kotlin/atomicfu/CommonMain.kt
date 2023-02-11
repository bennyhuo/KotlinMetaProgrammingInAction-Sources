package com.bennyhuo.kotlin.atomicfu

import kotlinx.atomicfu.atomic

/**
 * Created by benny.
 */
val counter = atomic(0)

fun commonMain() {
  counter.value = 0
  println(counter.value)


}