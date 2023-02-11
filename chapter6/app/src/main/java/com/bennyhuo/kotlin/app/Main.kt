package com.bennyhuo.kotlin.app

import com.bennyhuo.kotlin.app.data.Company
import com.bennyhuo.kotlin.app.data.District
import com.bennyhuo.kotlin.app.data.Location
import com.bennyhuo.kotlin.app.data.Speaker
import com.bennyhuo.kotlin.app.data.Talk

/**
 * Created by benny.
 */
fun main() {
  val talk = Talk(
    "如何优雅地使用数据类",
    Speaker(
      "bennyhuo 不是算命的",
      1,
      Company(
        "猿辅导",
        Location(39.9, 116.3),
        District("北京郊区")
      )
    )
  )

  println(talk)

  val location = Location(lat = 39.9, lng = 116.3)
  println(location)
}