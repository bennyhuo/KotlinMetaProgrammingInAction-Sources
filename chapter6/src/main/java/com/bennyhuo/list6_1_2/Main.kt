package com.bennyhuo.list6_1_2

// 6-1
data class Location(var lat: Double, var lng: Double)

fun main() {
  // 变量 lat 的值为 39.9，lng 的值为 116.3
  val (lat, lng) = Location(lat = 39.9, lng = 116.3)

  // 6-2
  val location = Location(lat = 39.9, lng = 116.3)
  // 输出：Location(lat=39.9, lng=116.3)
  println(location)
}