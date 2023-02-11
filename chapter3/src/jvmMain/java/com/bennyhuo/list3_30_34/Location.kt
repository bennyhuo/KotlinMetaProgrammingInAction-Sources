package com.bennyhuo.list3_30_34

// 3-30
data class Location(val lat: Double, val lng: Double) {
  override fun toString(): String {
    return "($lat, $lng)"
  }
}

class Render {
  fun append(element: () -> String): Render {
    return this
  }
}

fun main() {
  // 获取属性 lat 的引用
  Location::lat
  // 获取函数 toString 的引用
  Location::toString
  // 获取 Location 的构造函数的引用
  ::Location

  // 3-31
  val location = Location(39.0, 116.0)
  // invoke 调用时传入 receiver，输出为 (39.0, 116.0)
  println(Location::toString.invoke(location))

  // 3-32
  // invoke 调用时无须传入 receiver，输出为 (39.0, 116.0)
  println(location::toString.invoke())

  // 3-33
  val render = Render()
  render.append(location::toString)

  // 3-34
  // 将实例 location 绑定到属性引用上，类型为 KProperty0
  location::lat
  // 未绑定 receiver，类型为 KProperty1
  Location::lat
}