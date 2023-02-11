plugins {
  id("java")
  kotlin("jvm")
}

dependencies {
  implementation(kotlin("reflect"))
  implementation("org.jetbrains.kotlin:kotlin-compiler-embeddable")

  implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.4")
  implementation("com.bennyhuo.kotlin:grammar-antlr:${Version.grammarAntlr}")

  implementation("com.bennyhuo.kotlin:code-analyzer:${Version.kotlinCodeAnalyzer}")

  implementation("com.squareup:javapoet:${Version.javaPoet}")
  implementation("com.squareup:kotlinpoet:${Version.kotlinPoet}")
  implementation("com.squareup:kotlinpoet-ksp:${Version.kotlinPoet}")


  testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
  useJUnitPlatform()
}