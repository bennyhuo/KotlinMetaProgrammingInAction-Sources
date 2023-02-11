package com.bennyhuo.kotlin.annotations

// 5-43
@Target(AnnotationTarget.PROPERTY)
public annotation class SerialName(val name: String)
