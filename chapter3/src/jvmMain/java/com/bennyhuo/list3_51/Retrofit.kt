package com.bennyhuo.kotlin.reflect.retrofit

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

//region 3-51
data class Contributor(
  val login: String,
  val contributions: Int
)

interface GitHubService {
  @GET("/repos/{owner}/{repo}/contributors")
  suspend fun contributors(
    @Path("owner") owner: String,
    @Path("repo") repo: String
  ): List<Contributor>
}
//endregion

suspend fun main() {
  // 3-52
  val retrofit = Retrofit.Builder()
    .baseUrl("https://api.github.com/")
    .addConverterFactory(
      MoshiConverterFactory.create(
        Moshi.Builder()
          .addLast(KotlinJsonAdapterFactory())
          .build()
      )
    )
    .build()

  val service: GitHubService = retrofit.create(GitHubService::class.java)
  service.contributors(owner = "jetbrains", repo = "kotlin").forEach {
    println(it)
  }
}