package com.bennyhuo.list2_16

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

data class Location(
  @SerializedName("lat")
  val latitude: Double,
  @SerializedName("lng")
  val longitude: Double
)

fun main() {
  val location = Location(39.9091, 116.3975)
  println(Gson().toJson(location))
}
