package com.bennyhuo.list3_18_20

// 3-19
open class Task
class SubTask: Task()

fun main() {
  // 3-18 / 3-20
  Task::class // 类型为 KClass<Task>

  val task = Task()
  task::class // 类型为 KClass<out Task>
}