package com.bennyhuo.kotlin.apt

import com.bennyhuo.kotlin.annotations.Export
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
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic
import javax.tools.StandardLocation

// 5-56
class ExportProcessor : Processor {
  private lateinit var filer: Filer
  private lateinit var messager: Messager

  override fun init(processingEnv: ProcessingEnvironment) {
    filer = processingEnv.filer
    messager = processingEnv.messager
  }

  override fun getSupportedOptions(): Set<String> = emptySet()

  override fun getSupportedAnnotationTypes() = setOf(Export::class.java.name)

  override fun getSupportedSourceVersion() = SourceVersion.RELEASE_11

  override fun process(
    annotations: Set<TypeElement>,
    roundEnv: RoundEnvironment
  ): Boolean {
    val exports = annotations.flatMap {
      roundEnv.getElementsAnnotatedWith(it)
    }.filterIsInstance<TypeElement>()
      .onEach {
        if (Modifier.PUBLIC !in it.modifiers) {
          messager.printMessage(
            Diagnostic.Kind.ERROR,
            "@Export cannot be annotated at non-public classes.",
            it
          )
        }
      }

    if (exports.isEmpty()) return true

    val generatedFile = filer.createResource(
      StandardLocation.CLASS_OUTPUT,
      "",
      "java-symbol-exports.txt",
      *exports.toTypedArray()
    )

    generatedFile.openWriter().use { writer ->
      exports.forEach { element ->
        element.print(writer)
      }
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