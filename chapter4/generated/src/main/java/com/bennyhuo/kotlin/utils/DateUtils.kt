package com.bennyhuo.kotlin.utils

import java.util.Date

public fun Date.toCalendar() = DateUtils.toCalendar(this)

public fun Date.format(format: String) = DateUtils.format(this, format)

public fun Date.next() = DateUtils.next(this)

public fun Date.prev() = DateUtils.prev(this)

public fun Date.plus(days: Int) = DateUtils.plus(this, days)

public fun Date.minus(days: Int) = DateUtils.minus(this, days)
