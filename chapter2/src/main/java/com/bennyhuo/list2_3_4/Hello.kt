package com.bennyhuo.list2_3_4

/**
 * This is a class.
 * @sample com.bennyhuo.list2_3_4.sayHello
 * @since 1.0
 * @author bennyhuo
 */
@Deprecated("Do not use this.")
class Hello {
  // 这是一个函数
  fun greetings() {
    println("Hello there!!")
  }

  fun greetings2() {
    /* -- debug_start -- */
    println("[Debug] Hello!!")
    /* -- debug_end -- */
    println("[Release] Hello!!")
  }
}
