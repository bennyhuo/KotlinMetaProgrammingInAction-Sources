package com.bennyhuo.list3_36_42

import com.bennyhuo.list3_30_34.Location
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlin.reflect.jvm.javaType
import kotlin.reflect.typeOf

//region 3-42
inline fun <reified T : Any> Gson.fromJson(json: String): T {
  return fromJson(json, typeOf<T>().javaType)
}
//endregion

fun main() {
  //region 3-36
  val gson = Gson()
  println(
    gson.toJson(
      listOf(
        Location(39.0, 116.0),
        Location(39.0, 116.0),
        Location(39.0, 116.0),
        Location(39.0, 116.0)
      )
    )
  )


  val location = gson.fromJson(
    """{"lat":39.0,"lng":116.0}""",
    Location::class.java
  )
  println(location)
  //endregion

  //region 3-38
  val locations = gson.fromJson(
    """[{"lat":39.0,"lng":116.0},{"lat":40.0,"lng":128.0},{"lat":39.0,"lng":116.0},{"lat":39.0,"lng":116.0}]""",
    List::class.java
  )
  println(locations)
  //endregion

  //region 3-39
  val locations3 = gson.fromJson<List<Location>>(
    """[{"lat":39.0,"lng":116.0},{"lat":40.0,"lng":128.0},{"lat":39.0,"lng":116.0},{"lat":39.0,"lng":116.0}]""",
    object : TypeToken<List<Location>>() {}.type
  )
  //endregion

  //region 3-41
  val locations2 = gson.fromJson<List<Location>>(
    """[{"lat":39.0,"lng":116.0},{"lat":40.0,"lng":128.0},{"lat":39.0,"lng":116.0},{"lat":39.0,"lng":116.0}]""",
    typeOf<List<Location>>().javaType
  )
  println(locations2)
  //endregion

  val locations4 = gson.fromJson<List<Location>>(
    """[{"lat":39.0,"lng":116.0},{"lat":40.0,"lng":128.0},{"lat":39.0,"lng":116.0},{"lat":39.0,"lng":116.0}]""",
  )
  println(locations4)
}