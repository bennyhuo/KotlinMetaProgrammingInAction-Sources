package com.bennyhuo.list1_5

import com.bennyhuo.list1_3.GitUser
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

// 1-5
interface GitHubApi {

  @GET("/users/{login}")
  suspend fun getUserAsync(@Path("login") login: String): GitUser

  @GET("/users/{login}")
  fun getUser(@Path("login") login: String): retrofit2.Call<GitUser>

}

suspend fun main() {
  val api2 = Retrofit.Builder()
    .addConverterFactory(
      MoshiConverterFactory.create(
        Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
      )
    )
    .baseUrl("https://api.github.com")
    .build().create(GitHubApi::class.java)

  val user3 = api2.getUserAsync("bennyhuo")
  println(user3)
}
