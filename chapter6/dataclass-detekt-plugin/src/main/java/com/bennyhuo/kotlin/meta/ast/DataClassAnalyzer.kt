package com.bennyhuo.kotlin.meta.ast

import io.gitlab.arturbosch.detekt.api.CodeSmell
import io.gitlab.arturbosch.detekt.api.Config
import io.gitlab.arturbosch.detekt.api.Debt
import io.gitlab.arturbosch.detekt.api.Entity
import io.gitlab.arturbosch.detekt.api.Issue
import io.gitlab.arturbosch.detekt.api.Rule
import io.gitlab.arturbosch.detekt.api.RuleSet
import io.gitlab.arturbosch.detekt.api.RuleSetProvider
import io.gitlab.arturbosch.detekt.api.Severity
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.PropertyDescriptor
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameUnsafe
import org.jetbrains.kotlin.resolve.descriptorUtil.getAllSuperClassifiers

/**
 * Created by benny.
 */
private const val IGNORE_FQNAME = "com.bennyhuo.kotlin.common.Ignore"

private fun KtClass.subTypeOfIgnore(bindingContext: BindingContext): Boolean {
  val classDescriptor = bindingContext[
    BindingContext.DECLARATION_TO_DESCRIPTOR, this
  ] as? ClassDescriptor
  return classDescriptor?.getAllSuperClassifiers()
    ?.any { it.fqNameUnsafe.asString() == IGNORE_FQNAME } ?: false
}

private fun KtProperty.hasDelegateOrBackingField(bindingContext: BindingContext): Boolean {
  if (hasDelegate()) return true

  val propertyDescriptor = bindingContext[
    BindingContext.DECLARATION_TO_DESCRIPTOR, this
  ] as? PropertyDescriptor ?: return false
  return bindingContext[
    BindingContext.BACKING_FIELD_REQUIRED, propertyDescriptor
  ] ?: false
}

class ForbiddenDataClassesRuleSetProvider : RuleSetProvider {
  override val ruleSetId: String = javaClass.simpleName

  override fun instance(config: Config): RuleSet {
    return RuleSet(
      ruleSetId,
      listOf(ForbiddenDataClasses(config))
    )
  }
}

// 6-27
class ForbiddenDataClasses(config: Config) : Rule(config) {

  override val issue = Issue(
    javaClass.simpleName,
    Severity.CodeSmell,
    "This rule reports data classes which have properties with " +
      "delegates or backing fields declared in it without implement interface Ignore.",
    Debt.TEN_MINS
  )

  override fun visitClass(klass: KtClass) {
    super.visitClass(klass)
    // 6-28
    if (bindingContext == BindingContext.EMPTY) return

    if (
      klass.isData() &&
      !klass.subTypeOfIgnore(bindingContext) &&
      klass.getProperties().any {
        it.hasDelegateOrBackingField(bindingContext)
      }
    ) {
      report(
        CodeSmell(
          issue,
          Entity.from(klass, klass.nameIdentifier?.startOffsetInParent ?: 0),
          "DataClasses which declare properties with backing fields " +
            "or delegates are forbidden."
        )
      )
    }
  }
}