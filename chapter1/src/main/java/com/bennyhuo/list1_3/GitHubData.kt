package com.bennyhuo.list1_3

import com.squareup.moshi.Json

data class GitUser(
  val id: Int,
  val login: String,
  val url: String
)














data class GitRepo(
  val id: Int,
  val name: String,
  val owner: GitUser,
  val url: String,
  @Json(name = "stargazers_count")
  val stargazersCount: Int,
)
