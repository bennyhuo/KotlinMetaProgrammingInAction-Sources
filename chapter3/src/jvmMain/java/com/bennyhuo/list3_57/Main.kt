package com.bennyhuo.list3_57

data class Point(var x: Int, var y: Int) {
  // 希望为 Text 生成的 deepCopy 函数的等价写法
  fun deepCopy(
    x: Int = this.x,
    y: Int = this.y
  ) = Point(x, y)
}

data class Text(var id: Long, var text: String, var point: Point) {

  // 3-60
  // 希望为 Text 生成的 deepCopy 函数的等价写法
  fun deepCopy(
    id: Long = this.id, // id 为基本类型，无须深复制
    text: String = this.text, // text 为基本类型，无须深复制
    point: Point = this.point.deepCopy()
  ) = Text(id, text, point)
}

fun main() {
  val text = Text(0, "Kotlin", Point(10, 20))
  // 复制，但会共享一份 point
  val newText = text.copy(id = 1)
  newText.point.x = 100

  println(text)
}