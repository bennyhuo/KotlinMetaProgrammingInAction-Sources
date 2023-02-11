package com.bennyhuo.kotlin.serial

import com.bennyhuo.kotlin.annotations.Serializer

@Serializer(PersonSerializer::class)
data class Person(
  val id: Long,
  val name: String,
  val age: Int
)

class PersonSerializer