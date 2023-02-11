package com.bennyhuo.kotlin.atomicfu

import org.openjdk.jol.info.ClassLayout
import org.openjdk.jol.vm.VM
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicInteger

/**
 * Created by benny.
 */
fun main() {
  commonMain()

  printObjectLayout()
}

fun heavyTasks() {
  // 10-2
  val executor = Executors.newCachedThreadPool()
  val tasks = List(10000) {
    Task()
  }.onEach {
    executor.execute(it)
  }
}

fun printObjectLayout() {
  println(VM.current().details())

  println(ClassLayout.parseClass(Task::class.java).toPrintable())
  println(ClassLayout.parseInstance(Task()).toPrintable())
  println(ClassLayout.parseClass(AtomicInteger::class.java).toPrintable())
}