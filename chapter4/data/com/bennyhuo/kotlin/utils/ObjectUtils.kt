package com.bennyhuo.kotlin.utils

public fun <T : Any> List<T>.join(separator: String) = ObjectUtils.join(this, separator)
