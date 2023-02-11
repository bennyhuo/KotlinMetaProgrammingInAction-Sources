package com.bennyhuo.kotlin

import com.bennyhuo.kotlin.annotations.Serializable

// 7-44
@Serializable
class User(
  val id: Long,
  val name: String
)

class UserStore {
  // UserSerializer
  private val serializer = UserSerializer()
}
