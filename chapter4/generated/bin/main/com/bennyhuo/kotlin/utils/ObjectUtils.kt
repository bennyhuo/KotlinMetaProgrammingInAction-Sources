package com.bennyhuo.kotlin.utils

public fun <T : Any> List<T>.join(separator: String) = ObjectUtils.join(this, separator)

public fun <P1 : Number, P2 : Number, R> P1.call(p2: P2) where R : Number, R : Comparable<Number> =
    ObjectUtils.call(this, p2)
