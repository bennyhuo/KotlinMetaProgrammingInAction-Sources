package com.bennyhuo.kotlin.dataclass.inspection

import com.intellij.codeInspection.ProblemsHolder
import org.jetbrains.kotlin.descriptors.ClassDescriptor
import org.jetbrains.kotlin.descriptors.PropertyDescriptor
import org.jetbrains.kotlin.idea.caches.resolve.analyze
import org.jetbrains.kotlin.psi.KtClass
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.KtVisitorVoid
import org.jetbrains.kotlin.resolve.BindingContext
import org.jetbrains.kotlin.resolve.descriptorUtil.fqNameUnsafe
import org.jetbrains.kotlin.resolve.descriptorUtil.getAllSuperClassifiers
import org.jetbrains.kotlin.resolve.lazy.BodyResolveMode

// 6-30
class DataClassInspectionVisitor(
  private val holder: ProblemsHolder
) : KtVisitorVoid() {

  private fun KtClass.subTypeOfIgnore(): Boolean {
    val classDescriptor = analyze(BodyResolveMode.PARTIAL)[BindingContext.DECLARATION_TO_DESCRIPTOR, this] as? ClassDescriptor
    return classDescriptor?.getAllSuperClassifiers()
      ?.any { it.fqNameUnsafe.asString() == IGNORE_FQNAME } ?: false
  }

  private fun KtProperty.hasBackingField(): Boolean {
    // 6-31
    val bindingContext = analyze(BodyResolveMode.PARTIAL)
    val propertyDescriptor =
      bindingContext[BindingContext.DECLARATION_TO_DESCRIPTOR, this] as? PropertyDescriptor ?: return false
    return bindingContext[BindingContext.BACKING_FIELD_REQUIRED, propertyDescriptor]
      ?: false
  }

  override fun visitClass(klass: KtClass) {
    super.visitClass(klass)

    if (klass.isData() && !klass.subTypeOfIgnore()) {
      klass.getProperties().forEach {
        if (it.hasDelegate()) {
          report(it, "inspection.dataclass.error.value.delegate")
        } else if (it.hasBackingField()) {
          report(it, "inspection.dataclass.error.value.backingField")
        }
      }
    }
  }

  private fun report(ktProperty: KtProperty, key: String) {
    holder.registerProblem(
      ktProperty,
      DataClassBundle.message(key, ktProperty.name),
      RemovePropertyQuickFix(ktProperty),
      ImplementIgnoreQuickFix(ktProperty)
    )
  }
}