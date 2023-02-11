package com.bennyhuo.kotlin.annotations

import kotlin.reflect.KClass

// 5-49
@Target(AnnotationTarget.CLASS)
annotation class Serializer(val clazz: KClass<*>)
