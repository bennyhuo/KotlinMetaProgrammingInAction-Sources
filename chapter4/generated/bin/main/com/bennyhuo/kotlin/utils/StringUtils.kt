package com.bennyhuo.kotlin.utils

public fun List<String>.join(separator: String) = StringUtils.join(this, separator)

public fun Any?.toStringOrNull() = StringUtils.toStringOrNull(this)
