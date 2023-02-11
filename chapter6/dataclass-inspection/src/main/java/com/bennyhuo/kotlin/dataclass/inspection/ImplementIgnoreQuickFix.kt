package com.bennyhuo.kotlin.dataclass.inspection

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.idea.core.ShortenReferences
import org.jetbrains.kotlin.idea.inspections.KotlinUniversalQuickFix
import org.jetbrains.kotlin.idea.quickfix.KotlinQuickFixAction
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtProperty
import org.jetbrains.kotlin.psi.KtPsiFactory
import org.jetbrains.kotlin.psi.psiUtil.containingClass

// 6-33
class ImplementIgnoreQuickFix(ktProperty: KtProperty) :
  KotlinQuickFixAction<KtProperty>(ktProperty),
  KotlinUniversalQuickFix {

  override fun getFamilyName() = text

  override fun getText() =
    DataClassBundle.message("inspection.dataclass.quickfix.ignore")

  override fun invoke(project: Project, editor: Editor?, file: KtFile) {
    val ktClass = element?.containingClass() ?: return
    val ktPsiFactory = KtPsiFactory(project, markGenerated = true)
    val superTypeEntry = ktPsiFactory.createSuperTypeEntry(IGNORE_FQNAME)
    ktClass.addSuperTypeListEntry(superTypeEntry).let {
      ShortenReferences.DEFAULT.process(it)
    }
  }
}