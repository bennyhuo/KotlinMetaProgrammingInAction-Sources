package com.bennyhuo.list3_43

// 3-43
fun main() {
  // js 函数可以执行一段 JavaScript 代码并返回 dynamic 类型的结果
  val book: dynamic = js("{}")
  // 直接访问 dynamic 类型的成员，编译器不会有任何检查
  book.name = "Kotlin Metaprogramming"
  book.year = 2023
  // 输出：{"name":"Kotlin Metaprogramming","year":2023}
  println(JSON.stringify(book))
}