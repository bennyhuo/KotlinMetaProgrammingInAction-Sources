package com.bennyhuo.list2_32

import com.bennyhuo.lib.isNotNullAndNotEmpty

fun main() {
  val list: List<Int>? = listOf(1, 2, 3)
  // 如果启用 2-31 的配置，编译时会出现下面的错误
  // e: Unresolved reference: isNotNullAndNotEmpty
  if (list.isNotNullAndNotEmpty()) {
    list.forEach {
      println(it)
    }
  }
}