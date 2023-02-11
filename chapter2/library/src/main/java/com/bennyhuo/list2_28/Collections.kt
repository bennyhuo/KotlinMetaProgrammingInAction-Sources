package com.bennyhuo.lib

import kotlin.contracts.contract

/**
 * Created by benny.
 */
fun <E> Collection<E>?.isNotNullAndNotEmpty(): Boolean {
  contract {
    returns(true) implies (this@isNotNullAndNotEmpty != null)
  }
  return !this.isNullOrEmpty()
}