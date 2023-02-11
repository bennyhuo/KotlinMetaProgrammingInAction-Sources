package com.bennyhuo.kotlin

import com.bennyhuo.kotlin.annotations.Export

// 5-3
// 5-53
@Export
class Settings {
  private val map = HashMap<String, String>()

  fun set(key: String, value: String) {
    map[key] = value
  }

  fun get(key: String, default: String = ""): String {
    return map[key] ?: default
  }
}