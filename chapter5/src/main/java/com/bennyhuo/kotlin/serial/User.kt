package com.bennyhuo.kotlin.serial

import com.bennyhuo.kotlin.annotations.SerialName

// 5-42
class User(
  @SerialName("_id")
  val id: Long,
  @SerialName("_name")
  val name: String
)