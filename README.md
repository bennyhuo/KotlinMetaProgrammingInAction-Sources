# 《深入实践 Kotlin 元编程》随书源码

[机械工业出版社官方预售中，预售下单享 7 折](https://item.jd.com/10081757341486.html)

## 环境

* JDK 17
* IntelliJ IDEA 2022.3.2
* Kotlin 1.8.10

## 说明

本书绝大多数代码清单的内容都可以在这里找到。

书中限于篇幅，通常会对一些较长的代码做出省略，这里会尽可能地给出可以直接运行的程序。

为了方便读者找到对应的代码，我会将代码清单的编号作为包名，例如 com.bennyhuo.list4_20，表示代码清单 4-20 的代码在这个包中。如果包名不适合添加编号信息，我也会在代码前面用一行注释写出清单的编号，例如 `// 4-20`，表示以下代码对应于书中的代码清单 4-20。

当然，也有部分代码清单的代码属于某个开源项目，例如 KotlinTuples，书中对应的源码将不在这里列出，读者可以按照下面给出的链接找到对应的项目去阅读其中的源码。

如果遇到问题，也欢迎提 issue，我有空的时候会尽量帮你分析。

## 章节列表

### [第一章 元编程概述](chapter1)

* [JDK 源码编译环境配置 - 视频](https://www.bilibili.com/video/BV1Qj411P7eZ/)
* [Jetpack Compose 环境配置 - 视频](TODO)

### [第二章 元数据简介](chapter2)

### [第三章 运行时的反射](chapter3)

相关开源项目：

* [Retrofit](https://github.com/square/retrofit)
* [KotlinDeepCopy](https://github.com/bennyhuo/KotlinDeepCopy)
  * [JVM 反射实现](https://github.com/bennyhuo/KotlinDeepCopy/blob/master/reflect-impl/src/main/kotlin/com/bennyhuo/kotlin/deepcopy/reflect/DeepCopy.kt)
  * [JS 反射实现](https://github.com/bennyhuo/KotlinDeepCopy/blob/master/reflect-impl-js/src/main/kotlin/com/bennyhuo/kotlin/deepcopy/DeepCopy.kt)

### [第四章 源代码的生成](chapter4)

相关开源项目：

* [KotlinTuples](https://github.com/bennyhuo/KotlinTuples)
* [Anko-Legacy/anko-asm-parser](https://github.com/bennyhuo/anko-legacy)
* [Detekt](https://github.com/detekt/detekt)
* [JavaPoet](https://github.com/square/javapoet)
* [KotlinPoet](https://github.com/square/kotlinpoet)

### [第五章 编译时的符号处理](chapter5)

相关开源项目：

* [symbol-processing-module-support](https://github.com/bennyhuo/symbol-processing-module-support)
* [KotlinDeepCopy](https://github.com/bennyhuo/KotlinDeepCopy)
  * [APT](https://github.com/bennyhuo/KotlinDeepCopy/tree/master/compiler/compiler-apt)
  * [KSP](https://github.com/bennyhuo/KotlinDeepCopy/tree/master/compiler/compiler-ksp)

### [第六章 程序静态分析](chapter6)

* 使用 detekt 扫描程序，可以运行 :chapter6:app:detektMain 来查看效果。注意，运行所在的工作目录需要为 chapter6/app。
* 体验 dataclass-inspection 的效果，可以运行 :chapter6:dataclass-inspection:runIde 来运行一个调试用的 IntelliJ。

### 第七章 编译器插件

* allopen
  * [编译器插件源码](https://github.com/JetBrains/kotlin/tree/master/plugins/allopen)
  * [IntelliJ 插件源码](https://github.com/JetBrains/intellij-community/tree/master/plugins/kotlin/compiler-plugins/allopen)
* [TrimIndent](https://github.com/bennyhuo/Kotlin-Trim-Indent)
* [DeepCopy](https://github.com/bennyhuo/KotlinDeepCopy/tree/master/kcp-impl)
* [KAPT](https://github.com/jetbrains/kotlin/tree/master/plugins/kapt3)
* [KSP](https://github.com/google/ksp)

### 第八章 元程序的开发和调试

本章主要介绍了常见的单元测试框架的用法，涉及到的框架如下：

* [tschuchortdev/kotlin-compile-testing](https://github.com/tschuchortdev/kotlin-compile-testing)：Kotlin 编译器测试框架，原仓库
* [ZacSweers/kotlin-compile-testing](https://github.com/ZacSweers/kotlin-compile-testing)：Kotlin 编译器测试框架，Fork 仓库，由 moshi 的编译器插件作者/Kotlin 社区非常活跃的开发者维护，版本更新更快
* [bennyhuo/kotlin-compile-testing-extensions](https://github.com/bennyhuo/kotlin-compile-testing-extensions)：Kotlin 编译器测试框架的扩展，简化了测试任务的集成流程，提供了较为灵活的结果检查机制

所有单测 case 的编写方法可以参见 DeepCopy 项目的单测：
* [KAPT/KSP](https://github.com/bennyhuo/KotlinDeepCopy/tree/master/test-common/src/test/kotlin/com/benyhuo/kotlin/deepcopy/compiler)
* [KCP](https://github.com/bennyhuo/KotlinDeepCopy/tree/master/kcp-impl/compiler-kcp/src/test/kotlin/com/bennyhuo/kotlin/kcp/deepcopy/compiler)

### 第九章 Jetpack Compose 的编译时处理

本章源码在 AOSP 中，读者可以直接在 AOSP 在线源代码阅读平台上直接阅读这部分源码：[androidx-main:compose](https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:compose/compiler/)，也可以按照本书第一章提供的方法下载源码阅读。

### [第十章  AtomicFU 的编译产物处理](chapter10)

本章涉及到的 AtomicFU 的源码可以参见我 Fork 的版本：[kotlinx-atomicfu](https://github.com/bennyhuo/kotlinx-atomicfu)
