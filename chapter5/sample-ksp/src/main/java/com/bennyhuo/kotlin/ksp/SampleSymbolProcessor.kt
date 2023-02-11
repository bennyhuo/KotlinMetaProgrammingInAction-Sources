package com.bennyhuo.kotlin.ksp

import com.bennyhuo.kotlin.annotations.Sample
import com.bennyhuo.kotlin.annotations.SerialName
import com.bennyhuo.kotlin.annotations.Serializer
import com.google.devtools.ksp.KSTypeNotPresentException
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.getClassDeclarationByName
import com.google.devtools.ksp.getFunctionDeclarationsByName
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSType
import com.google.devtools.ksp.symbol.KSTypeReference
import com.google.devtools.ksp.symbol.Variance

// 5-14
class SampleSymbolProcessor(val environment: SymbolProcessorEnvironment) :
  SymbolProcessor {
  override fun process(resolver: Resolver): List<KSAnnotated> {

    val decls = resolver.getSymbolsWithAnnotation(Sample::class.java.name)

    run {
      // 5-20
      val fqName = "com.bennyhuo.kotlin.sample.A"
      // A 类的符号，ksClass 的类型是 KSClassDeclaration?
      val ksClass = resolver.getClassDeclarationByName(fqName)

      println(ksClass)
    }

    run {
      // 5-21
      // C 的成员函数 c 的全名
      val fqName = "com.bennyhuo.kotlin.sample.C.c"
      // ksFunctions 的类型是 Sequence<KSFunctionDeclaration>，包含所有重载函数
      val ksFunctions = resolver.getFunctionDeclarationsByName(fqName)

      println(ksFunctions.toList())
    }

    run {
      // 5-25
      val fqName = "com.bennyhuo.kotlin.sample.X"
      // X 类的符号
      val declaration = resolver.getClassDeclarationByName(fqName)
      // 输出：X
      println(declaration?.asStarProjectedType())
    }

    run {
      val fqName = "com.bennyhuo.kotlin.sample.X.c"
      // 5-31
      // 函数符号
      val ksFunction: KSFunctionDeclaration = resolver.getFunctionDeclarationsByName(fqName).first()
      // returnType 是类型引用符号
      val returnType: KSTypeReference? = ksFunction.returnType

      println(returnType)
      // 5-32
      val ksType: KSType? = returnType?.resolve()
      println(ksType)
    }

    run {
      // 5-40
      val list = resolver.getClassDeclarationByName("kotlin.collections.List")!!
      // List<out Int>
      val listInt = list.asType(
        listOf(
          resolver.getTypeArgument(
            resolver.createKSTypeReferenceFromKSType(resolver.builtIns.intType),
            Variance.COVARIANT
          )
        )
      )

      // List<out String>
      val listString =
        list.asType(
          listOf(
            resolver.getTypeArgument(
              resolver.createKSTypeReferenceFromKSType(resolver.builtIns.stringType),
              Variance.COVARIANT
            )
          )
        )

      println(listInt.starProjection() == listString.starProjection())
    }

    run {
      // 5-47
      val className = "com.bennyhuo.kotlin.annotations.SerialName"
      // 获取 @SerialName 的符号
      val serialName = resolver.getClassDeclarationByName(className)
      resolver.getSymbolsWithAnnotation(className).forEach {
        it.annotations.singleOrNull {
          // annotationType 是 KSTypeReference，表示对类型的一个引用符号
          // resolve 函数可以解析 KSTypeReference 得到对应的类型
          it.annotationType.resolve().declaration == serialName
        }?.arguments?.forEach {
          environment.logger.warn("${it.name?.asString()}: ${it.value}")
        }
      }

      // 5-48
      resolver.getSymbolsWithAnnotation(className).forEach {
        val name = it.getAnnotationsByType(SerialName::class).firstOrNull()?.name
        environment.logger.warn("name: $name")
      }
    }

    run {
      // 5-51
      resolver.getSymbolsWithAnnotation(Serializer::class.java.name).forEach {
        try {
          val clazz = it.getAnnotationsByType(Serializer::class).first().clazz
          println("class: $clazz")
        } catch (e: KSTypeNotPresentException) {
          val type = e.ksType
          println("type: $type")
        }
      }
    }

    return emptyList()
  }

  override fun finish() {
    super.finish()
  }

  override fun onError() {
    super.onError()
  }

  fun println(message: Any?) {
    environment.logger.warn(message.toString())
  }
}