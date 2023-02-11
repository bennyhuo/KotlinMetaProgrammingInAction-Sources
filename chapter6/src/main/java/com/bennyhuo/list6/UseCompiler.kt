package com.bennyhuo.kotlin.meta.ast

/**
 * Created by benny.
 */
import com.bennyhuo.kotlin.analyzer.KotlinCodeAnalyzer
import com.bennyhuo.kotlin.analyzer.buildOptions
import com.bennyhuo.kotlin.analyzer.utils.lineAndColumn
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.PropertyDescriptor
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameUnsafe
import org.jetbrains.kotlin.resolve.descriptorUtil.getAllSuperClassifiers
import java.io.File

private const val IGNORE_FQNAME = "Ignore"

// 6-24
private fun KtClass.subTypeOfIgnore(bindingContext: BindingContext): Boolean {
  val classDescriptor = bindingContext[
    BindingContext.DECLARATION_TO_DESCRIPTOR, this
  ] as? ClassDescriptor
  return classDescriptor?.getAllSuperClassifiers()
    ?.any { it.fqNameUnsafe.asString() == IGNORE_FQNAME } ?: false
}

// 6-25
private fun KtProperty.hasDelegateOrBackingField(bindingContext: BindingContext): Boolean {
  if (hasDelegate()) return true

  val propertyDescriptor = bindingContext[
    BindingContext.DECLARATION_TO_DESCRIPTOR, this
  ] as? PropertyDescriptor ?: return false
  return bindingContext[
    BindingContext.BACKING_FIELD_REQUIRED, propertyDescriptor
  ] ?: false
}

private fun scanForDataClasses(sourceRoot: String): Sequence<DataClassInfo> {
  // 6-21
  val analysisResult = KotlinCodeAnalyzer(buildOptions {
    inputPaths = listOf(sourceRoot)
    inheritClassPath = true
  }).analyze()

  // 6-23
  return analysisResult.files.flatMap {
    it.declarations
  }.asSequence()
    .filterIsInstance<KtClass>()
    .filter {
      it.isData()
    }.filter {
      !it.subTypeOfIgnore(analysisResult.bindingContext)
    }.filter {
      it.getProperties().any {
        it.hasDelegateOrBackingField(analysisResult.bindingContext)
      }
    }.map {
      DataClassInfo(
        File(it.containingKtFile.virtualFile.path),
        it.lineAndColumn().first,
        it.fqName?.asString() ?: "<No Class Name>"
      )
    }
}

fun main() {
  val sourceRoot = "chapter6/data/6-19"

  scanForDataClasses(sourceRoot).forEach {
    println("(${it.file}:${it.line}) data class ${it.className}")
  }
}