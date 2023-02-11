package com.bennyhuo.kotlin.ksp

import com.bennyhuo.kotlin.annotations.Export
import com.google.devtools.ksp.isPublic
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration

// 5-57
class ExportSymbolProcessor(
  val environment: SymbolProcessorEnvironment
) : SymbolProcessor {

  override fun process(resolver: Resolver): List<KSAnnotated> {
    val exports = resolver.getSymbolsWithAnnotation(Export::class.java.name)
      .filterIsInstance<KSClassDeclaration>()
      .onEach {
        if (!it.isPublic()) {
          environment.logger.error(
            "@Export cannot be annotated at non-public classes.",
            it
          )
        }
      }.toList()

    environment.logger.warn(exports.joinToString { it.qualifiedName!!.asString() })

    if (exports.isEmpty()) return emptyList()

    val generatedFileStream = environment.codeGenerator.createNewFile(
      Dependencies(
        true,
        *exports.mapNotNull { it.containingFile }.toTypedArray()
      ),
      "",
      "kotlin-symbol-exports",
      "txt"
    )

    generatedFileStream.bufferedWriter().use { writer ->
      exports.forEach { declaration ->
        declaration.print(writer)
      }
    }

    return emptyList()
  }
}