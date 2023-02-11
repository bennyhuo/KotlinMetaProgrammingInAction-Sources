package com.bennyhuo.kotlin.apt

import com.bennyhuo.kotlin.annotations.Serializable
import com.squareup.javapoet.JavaFile
import com.squareup.javapoet.TypeSpec
import javax.annotation.processing.Completion
import javax.annotation.processing.Filer
import javax.annotation.processing.Messager
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.AnnotationMirror
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements

/**
 * Created by benny.
 */
class SerializerProcessor : Processor {
  private lateinit var filer: Filer
  private lateinit var messager: Messager
  private lateinit var elements: Elements

  override fun init(processingEnv: ProcessingEnvironment) {
    filer = processingEnv.filer
    messager = processingEnv.messager
    elements = processingEnv.elementUtils
  }

  override fun getSupportedOptions(): Set<String> = emptySet()

  override fun getSupportedAnnotationTypes() =
    setOf(Serializable::class.java.name)

  override fun getSupportedSourceVersion() = SourceVersion.RELEASE_11

  override fun process(
    annotations: Set<TypeElement>,
    roundEnv: RoundEnvironment
  ): Boolean {

    annotations.flatMap {
      roundEnv.getElementsAnnotatedWith(it)
    }.filterIsInstance<TypeElement>()
      .forEach {
        val packageName = elements.getPackageOf(it).qualifiedName.toString()
        val simpleName = it.simpleName.toString()

        JavaFile.builder(
          packageName,
          TypeSpec.classBuilder("${simpleName}Serializer")
            .addOriginatingElement(it)
            .build()
        ).build().writeTo(filer)
      }

    return true
  }


  override fun getCompletions(
    element: Element?,
    annotation: AnnotationMirror?,
    member: ExecutableElement?,
    userText: String?
  ): Iterable<Completion> {
    return emptyList()
  }
}