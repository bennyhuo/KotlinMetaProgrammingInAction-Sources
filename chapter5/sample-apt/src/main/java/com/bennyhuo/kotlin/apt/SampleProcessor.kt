package com.bennyhuo.kotlin.apt

import com.bennyhuo.kotlin.annotations.Sample
import com.bennyhuo.kotlin.annotations.SerialName
import com.bennyhuo.kotlin.annotations.Serializer
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.Filer
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.type.MirroredTypeException
import javax.lang.model.type.TypeKind
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import kotlin.reflect.KClass


// 5-5
class SampleProcessor : AbstractProcessor() {
  private lateinit var filer: Filer
  private lateinit var messager: Messager
  private lateinit var types: Types
  private lateinit var elements: Elements

  override fun init(processingEnv: ProcessingEnvironment) {
    filer = processingEnv.filer
    messager = processingEnv.messager
    types = processingEnv.typeUtils
    elements = processingEnv.elementUtils

    // 5-9
    val optionA = processingEnv.options["optionA"]
    println(optionA) // A
  }

  override fun process(
    annotations: Set<TypeElement>,
    roundEnv: RoundEnvironment
  ): Boolean {
    println("processing Sample ...")

    // 5-11
    val annotatedElements = annotations.flatMap { annotation: TypeElement ->
      // 注意 annotation 实际上是注解的符号类型
      roundEnv.getElementsAnnotatedWith(annotation)
    }

    // [com.bennyhuo.kotlin.sample.A, getB$annotations(), c()]
    println(annotatedElements)

    // 5-12
    // Sample 是注解类
    val annotatedElements2 =
      roundEnv.getElementsAnnotatedWith(Sample::class.java)

    // [com.bennyhuo.kotlin.sample.A, getB$annotations(), c()]
    println(annotatedElements2)


    run {
      // 5-17
      val element = annotatedElements.firstOrNull() ?: return false
      val modifiers = element.modifiers

      println("Modifiers of $element:$modifiers")

      // 5-18
      if (Modifier.PUBLIC in element.modifiers) {
        println("$element is public.")
      }
    }

    run {
      // 5-19
      // typeElement 的类型是 TypeElement，获取到类对应的符号
      val typeElement = elements.getTypeElement("com.bennyhuo.kotlin.sample.A")
      // packageElement 的类型是 PackageElement，获取到包对应的符号
      val packageElement = elements.getPackageElement("com.bennyhuo.kotlin")

      println("TypeElement: $typeElement")
      println("PackageElement: $packageElement")
    }

    run {
      // 5-23
      val element: TypeElement =
        elements.getTypeElement("com.bennyhuo.kotlin.sample.X")
      // X 的符号的类型
      val type: TypeMirror = element.asType()
      // 输出：com.bennyhuo.kotlin.sample.X
      println(type)

      // 5-24
      element.enclosedElements.forEach {
        when (it) {
          // 如果是字段
          is VariableElement -> {
            // it.asType() 可以获取到字段的类型
            println("field: ${it.simpleName}: ${it.asType()}")
          }
          // 如果是方法
          is ExecutableElement -> {
            // 打印方法名和方法的类型
            println("method: ${it.simpleName}, type: ${it.asType()}")
            // 遍历方法的参数
            it.parameters.forEachIndexed { i, e ->
              // e 的类型是 VariableElement
              println("parameter: $i, ${e.simpleName}: ${e.asType()}")
            }
          }
        }
      }
    }

    run {
      // String 类型
      val stringType = elements.getTypeElement("java.lang.String").asType()
      // List 符号
      val typeElement = elements.getTypeElement("java.util.List")
      // List<String> 类型，输出：java.util.List<java.lang.String>
      println(types.getDeclaredType(typeElement, stringType))
    }


    run {
      // 5-34
      val element: Element =
        elements.getTypeElement("com.bennyhuo.kotlin.sample.A")
      // 获取符号的类型
      val type: TypeMirror = element.asType()
      // 获取类型的符号
      val elementFromType: Element = types.asElement(type)
      // 输出 true
      println(elementFromType === element)
    }

    run {
      // 5-37
      // List 符号
      val typeElement = elements.getTypeElement("java.util.List")

      // String 类型
      val stringType = elements.getTypeElement("java.lang.String").asType()
      // List<String> 类型
      val stringListType = types.getDeclaredType(typeElement, stringType)

      // int
      val intType = types.getPrimitiveType(TypeKind.INT)
      // Integer
      val boxedIntType = types.boxedClass(intType).asType()
      // List<Integer>
      val intListType = types.getDeclaredType(typeElement, boxedIntType)

      println(stringListType)
      println(intListType)

      println(
        "List<String> vs List<Integer>: ${
          types.isSameType(
            stringListType,
            intListType
          )
        }"
      )
      println(
        "List vs List: ${
          types.isSameType(
            types.erasure(stringListType), types.erasure(intListType)
          )
        }"
      )

      println(types.erasure(stringListType) === types.erasure(intListType))
    }

    run {
      val intType = types.getPrimitiveType(TypeKind.INT)
      val longType = types.getPrimitiveType(TypeKind.LONG)
      val boxedIntType = types.boxedClass(intType).asType()
      val boxedLongType = types.boxedClass(longType).asType()
      println(intType)
      println(longType)
      // true, int 与 long 存在可赋值关系
      println(types.isAssignable(intType, longType))
      // true, int 与 Integer 存在可赋值关系
      println(types.isAssignable(intType, boxedIntType))
      // true, int 是 long 的子类型
      println(types.isSubtype(intType, longType))
      // false, int 不是 Integer 的子类型
      println(types.isSubtype(intType, boxedIntType))
    }

    run {
      // 5-44
      val serialName = elements.getTypeElement(
        "com.bennyhuo.kotlin.annotations.SerialName"
      )
      // 通过注解符号获取被标注的符号
      val propertyElements = roundEnv.getElementsAnnotatedWith(serialName)

      // 5-45
      propertyElements.forEach {element ->
        // 可能有多个注解，先找到 @SerialName
        element.annotationMirrors.singleOrNull {
          // annotationType 返回注解的类型，通过它可以获取到注解的符号
          // serialName 是之前获取到的 @SerialName 的符号
          it.annotationType.asElement() == serialName
        }?.elementValues?.forEach {
          // it.key 是注解参数符号
          // it.value 是 AnnotationValue 类型
          // it.value.value 获取到的就是注解当中参数的值
          println("${it.key.simpleName}: ${it.value.value}")
        }
      }

      // 5-46
      roundEnv.getElementsAnnotatedWith(SerialName::class.java)
        .forEach { element ->
          val name = element.getAnnotation(SerialName::class.java)?.name
          println("name: $name")
        }
    }

    run {
      // 5-50
      roundEnv.getElementsAnnotatedWith(Serializer::class.java)
        .filterIsInstance<TypeElement>()
        .map {
          it.getAnnotation(Serializer::class.java)
        }.forEach {
          try {
            val clazz: KClass<*> = it.clazz
            println("class: $clazz")
          } catch (e: MirroredTypeException) {
            val typeMirror = e.typeMirror
            println("type: $typeMirror")
          }
        }
    }

    return false
  }

  override fun getSupportedOptions(): Set<String> = emptySet()

  override fun getSupportedAnnotationTypes() = setOf(
    Sample::class.java.name,
    Serializer::class.java.name
  )

  override fun getSupportedSourceVersion() = SourceVersion.RELEASE_11

}