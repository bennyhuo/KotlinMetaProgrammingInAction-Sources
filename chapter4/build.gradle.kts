plugins {
  id("java")
  kotlin("jvm")
}

dependencies {
  implementation(kotlin("reflect"))
  implementation("org.jetbrains.kotlinx:kotlinx-cli:0.3.4")

  //region 4-24
  implementation("com.bennyhuo.kotlin:anko-asm-parser:0.1")
  implementation("org.jtwig:jtwig-core:5.65")
  implementation("org.apache.commons:commons-lang3:3.1")
  //endregion

  implementation("com.squareup:javapoet:${Version.javaPoet}")
  implementation("com.squareup:kotlinpoet:${Version.kotlinPoet}")
  implementation("com.squareup:kotlinpoet-ksp:${Version.kotlinPoet}")


  testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
  useJUnitPlatform()
}