package com.bennyhuo.list6

import com.bennyhuo.kotlin.meta.ast.DataClassInfo
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.ListTokenSource
import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.TerminalNode
import org.jetbrains.kotlin.spec.grammar.KotlinLexer
import org.jetbrains.kotlin.spec.grammar.KotlinParser
import org.jetbrains.kotlin.spec.grammar.KotlinParser.ClassDeclarationContext
import org.jetbrains.kotlin.spec.grammar.KotlinParser.KotlinFileContext
import org.jetbrains.kotlin.spec.grammar.KotlinParser.PropertyDeclarationContext
import java.io.File

/**
 * 6-17
 * ClassDeclarationContext 是语法分析得到的类对应的语法结构
 * classDeclaration
 *  : modifiers? (CLASS | (FUN NL*)? INTERFACE) NL* simpleIdentifier
 *  (NL* typeParameters)? (NL* primaryConstructor)?
 *  (NL* COLON NL* delegationSpecifiers)?
 *  (NL* typeConstraints)?
 *  (NL* classBody | NL* enumClassBody)?
 *  ;
 */
class ClassDeclaration(context: ClassDeclarationContext) {
  // 通过 simpleIdentifier 来获取类名
  val className: String =
    context.simpleIdentifier()?.Identifier()?.text ?: "<No ClassName>"

  // 数据类一定有 CLASS，我们直接取它所在的行号作为类的行号
  val line: Int = context.CLASS().symbol.line

  // 通过读取修饰关键字来判断是否为数据类
  private val isDataClass = context.modifiers()?.modifier()
    ?.firstOrNull { it.classModifier()?.DATA() != null } != null

  // 结构稍微有点复杂，通过读取 classBody 当中的成员来找到 property
  private val properties = context.classBody()
    ?.classMemberDeclarations()
    ?.classMemberDeclaration()
    ?.mapNotNull {
      it.declaration()?.propertyDeclaration()
    }?.map {
      PropertyDeclaration(it)
    } ?: emptyList()

  // 数据类，且存在至少一个属性被判定为不适合定义在数据类当中
  val isInvalidDataClass: Boolean
    get() = isDataClass && properties.any { it.isInvalidInDataClass() }
}

/**
 * 6-16
 * PropertyDeclarationContext 是语法分析得到的属性对应的结构
 * propertyDeclaration
 *   : modifiers? (VAL | VAR)
 *   (NL* typeParameters)?
 *   (NL* receiverType NL* DOT)?
 *   (NL* (multiVariableDeclaration | variableDeclaration))
 *   (NL* typeConstraints)?
 *   (NL* (ASSIGNMENT NL* expression | propertyDelegate))?
 *   (NL+ SEMICOLON)? NL* (getter? (NL* semi? setter)? | setter? (NL* semi? getter)?)
 *   ;
 */
class PropertyDeclaration(private val context: PropertyDeclarationContext) {
  fun isInvalidInDataClass(): Boolean {
    // 禁止属性委托
    if (context.propertyDelegate() != null) return true
    // 没有 getter，必然存在 backing-field
    if (context.getter() == null) return true
    if (
    // 不存在 val，必然是 var
      context.VAL() == null &&
      // var 属性的 setter 为 null，必然存在 backing-field
      context.setter() == null
    ) {
      return true
    }

    // getter 当中使用了 field，必然存在 backing-field
    if (context.getter()?.functionBody()
        ?.findRecursively(KotlinLexer.FIELD) != null
    ) {
      return true
    }

    // setter 当中使用了 field，必然存在 backing-field
    if (context.setter()?.functionBody()
        ?.findRecursively(KotlinLexer.FIELD) != null
    ) {
      return true
    }

    return false
  }

  fun ParserRuleContext.findRecursively(tokenType: Int): TerminalNode? {
    return getToken(tokenType, 0)
      ?: children.filterIsInstance<ParserRuleContext>()
        .asSequence().mapNotNull {
          it.findRecursively(tokenType)
        }.firstOrNull()
  }
}

// 6-18
private fun scanForDataClasses(file: File): Sequence<DataClassInfo> {
  // 构造词法分析器
  val lexer = KotlinLexer(CharStreams.fromStream(file.inputStream()))
  val tokens = ListTokenSource(lexer.allTokens)
  // 构造语法分析器，并将 tokens 解析为 KotlinFileContext
  val parser = KotlinParser(CommonTokenStream(tokens))
  val kotlinFile: KotlinFileContext = parser.kotlinFile()

  // 按照语法结构找到 classDeclaration
  return kotlinFile.topLevelObject().asSequence().map {
    it.declaration()?.classDeclaration()
  }.filterIsInstance<ClassDeclarationContext>()
    // 只处理类，忽略接口
    .filter { it.CLASS() != null }
    .map { ClassDeclaration(it) }
    .filter {
      it.isInvalidDataClass
    }.map {
      DataClassInfo(file, it.line, it.className)
    }
}

fun main() {

  val sourceRoot = File("chapter6/data")
  sourceRoot.walkTopDown().filter {
    it.isFile && it.name.endsWith(".kt")
  }.forEach { file ->
    scanForDataClasses(file).forEach {
      println("(${it.file}:${it.line}) data class ${it.className}")
    }
  }
}