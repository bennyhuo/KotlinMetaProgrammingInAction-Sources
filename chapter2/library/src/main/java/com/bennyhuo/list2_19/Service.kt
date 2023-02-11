package com.bennyhuo.lib

// 2-19
interface Service {
  companion object {
    val sharedService: Service = ServiceImpl()
  }

  val id: Long

  fun getName(): String

}

class ServiceImpl : Service {

  override val id = 0L

  override fun getName() = "benny"

}
