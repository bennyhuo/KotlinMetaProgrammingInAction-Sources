package com.bennyhuo.list1_3

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

// 1-3
class GitHubApi {

  private val endPoint = "https://api.github.com"
  private val client = OkHttpClient()
  private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

  fun getUser(login: String): GitUser? {
    val request: Request = Request.Builder()
      .url("$endPoint/users/$login").get().build()
    return client.newCall(request).execute().use { response ->
      response.body?.string()?.let {
        moshi.adapter(GitUser::class.java).fromJson(it)
      }
    }
  }

  //region 1-4
  fun getUser(
    login: String,
    onSuccess: (GitUser?) -> Unit,
    onFailure: (Throwable) -> Unit
  ) {
    val request: Request = Request.Builder()
      .url("$endPoint/users/$login").get().build()
    client.newCall(request).enqueue(object : Callback {
      override fun onFailure(call: Call, e: IOException) {
        onFailure(e)
      }

      override fun onResponse(call: Call, response: Response) {
        try {
          val user = response.body?.string()?.let {
            moshi.adapter(GitUser::class.java).fromJson(it)
          }
          onSuccess(user)
        } catch (e: Exception) {
          onFailure(e)
        }
      }
    })
  }

  suspend fun getUserAsync(login: String): GitUser? =
    suspendCoroutine { continuation ->
      getUser(login, onSuccess = {
        continuation.resume(it)
      }, onFailure = {
        continuation.resumeWithException(it)
      })
    }
  //endregion

  fun getRepository(userLogin: String, repoName: String): GitRepo? {
    val request: Request = Request.Builder()
      .url("$endPoint/repos/$userLogin/$repoName").get().build()
    return client.newCall(request).execute().use { response ->
      response.body?.string()?.let {
        moshi.adapter(GitRepo::class.java).fromJson(it)
      }
    }
  }

}



suspend fun main() {
  val api = GitHubApi()
  val user = api.getUser("bennyhuo")
  println(user)

  val repo = api.getRepository("bennyhuo", "KotlinDeepCopy")
  println(repo)

  val user2 = api.getUserAsync("bennyhuo")
  println(user2)
}
