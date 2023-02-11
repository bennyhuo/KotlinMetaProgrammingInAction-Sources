package com.bennyhuo.kotlin.atomicfu

import kotlinx.atomicfu.atomic
import kotlinx.atomicfu.getAndUpdate

// 10-4
class TaskAtomicFU : Runnable {

  private val state = atomic(STATE_READY)

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
