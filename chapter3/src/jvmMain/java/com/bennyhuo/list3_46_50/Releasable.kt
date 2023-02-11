package com.bennyhuo.list3_46_50

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty0
import kotlin.reflect.jvm.isAccessible

//region 3-47
fun <T : Any> releasableNotNull() = ReleasableNotNull<T>()

class ReleasableNotNull<T : Any> : ReadWriteProperty<Any?, T> {
  private var value: T? = null

  override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    this.value = value
  }

  override fun getValue(thisRef: Any?, property: KProperty<*>): T {
    return value ?: error("...")
  }

  fun isInitialized() = value != null

  fun release() {
    value = null
  }
}
//endregion

//region 3-50
val <R> KProperty0<R>.isInitialized: Boolean
  get() {
    isAccessible = true
    // 获取委托对象，并且调用它的 isInitialized 函数
    return (getDelegate() as? ReleasableNotNull<*>)?.isInitialized()
      ?: throw IllegalAccessException("...")
  }

fun <R> KProperty0<R>.release() {
  isAccessible = true
  // 获取委托对象，并且调用它的 release 函数
  return (getDelegate() as? ReleasableNotNull<*>)?.release()
    ?: throw IllegalAccessException("...")
}
//endregion

class Image

fun createImage() = Image()

class ImageLoader {

  // 3-48
  // 定义变量 image，类型为 Image
  private var image: Image by releasableNotNull()

  val isImageLoaded: Boolean
    // 3-49
    // 获取属性引用，并判断属性是否被初始化
    get() = ::image.isInitialized

  fun load() {
    // 3-48
    // 初始化变量 image
    image = createImage()
  }

  fun release() {
    // 3-49
    // 获取属性引用，并释放属性的值
    ::image.release()
  }

}

fun main() {
  val loader = ImageLoader()
  println(loader.isImageLoaded)
  loader.load()
  println(loader.isImageLoaded)
  loader.release()
  println(loader.isImageLoaded)
}