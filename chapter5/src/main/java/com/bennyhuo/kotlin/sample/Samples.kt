package com.bennyhuo.kotlin.sample

import com.bennyhuo.kotlin.annotations.Sample

@Sample
class A

class B {
  @Sample
  val b = 0
}

class C {
  @Sample
  fun c() {

  }

  fun c(int: Int) {

  }
}

// 5-22
class X {
  val a: Int = 0
  val b: String = ""
  fun c(i: Int, j: Int): Int {
    return i + j
  }
}