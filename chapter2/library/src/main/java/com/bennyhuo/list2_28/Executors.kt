package com.bennyhuo.lib

import java.util.concurrent.Executors

/**
 * Created by benny.
 */
val ioExecutor = Executors.newFixedThreadPool(
  Runtime.getRuntime().availableProcessors() * 2
)

val defaultExecutor = Executors.newCachedThreadPool()

val scheduledExecutor = Executors.newScheduledThreadPool(1)