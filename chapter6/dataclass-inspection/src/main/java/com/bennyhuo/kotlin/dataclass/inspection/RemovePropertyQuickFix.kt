package com.bennyhuo.kotlin.dataclass.inspection

import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import org.jetbrains.kotlin.idea.inspections.KotlinUniversalQuickFix
import org.jetbrains.kotlin.idea.quickfix.KotlinQuickFixAction
import org.jetbrains.kotlin.psi.KtFile
import org.jetbrains.kotlin.psi.KtProperty

// 6-32
class RemovePropertyQuickFix(ktProperty: KtProperty) :
  KotlinQuickFixAction<KtProperty>(ktProperty),
  KotlinUniversalQuickFix {

  override fun getFamilyName() = text

  override fun getText() =
    DataClassBundle.message("inspection.dataclass.quickfix.removeProperty")

  override fun invoke(project: Project, editor: Editor?, file: KtFile) {
    element?.delete()
  }
}