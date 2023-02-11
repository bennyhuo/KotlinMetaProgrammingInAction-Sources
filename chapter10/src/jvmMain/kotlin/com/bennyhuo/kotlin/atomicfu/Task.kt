package com.bennyhuo.kotlin.atomicfu

import java.util.concurrent.atomic.AtomicInteger

// 10-1
const val STATE_READY = 0 // 已就绪
const val STATE_WORKING = 1 // 工作中
const val STATE_DONE = 2 // 已完成
const val STATE_CANCELLED = -1 // 已取消

class Task : Runnable {

  private val state = AtomicInteger(STATE_READY)

  fun start() {
    val prev = state.getAndUpdate { prev ->
      when (prev) {
        STATE_READY -> STATE_WORKING
        else -> prev
      }
    }
    if (prev == STATE_READY) {
      // notify started.
    }
  }

  fun cancel() {
    val prev = state.getAndUpdate { prev ->
      when (prev) {
        STATE_READY, STATE_WORKING -> STATE_CANCELLED
        else -> prev
      }
    }

    if (prev == STATE_READY || prev == STATE_WORKING) {
      // notify cancelled.
    }
  }

  override fun run() {
    TODO("Not yet implemented")
  }
}