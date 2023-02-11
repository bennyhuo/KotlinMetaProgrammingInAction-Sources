package com.bennyhuo.kotlin.dataclass.inspection

import com.intellij.codeInspection.ProblemsHolder
import com.intellij.psi.PsiElementVisitor
import org.jetbrains.kotlin.idea.inspections.AbstractKotlinInspection

// 6-29
class DataClassInspection : AbstractKotlinInspection() {

  override fun buildVisitor(
    holder: ProblemsHolder,
    isOnTheFly: Boolean
  ): PsiElementVisitor {
    return DataClassInspectionVisitor(holder)
  }

}