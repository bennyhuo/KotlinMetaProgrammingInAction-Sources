plugins {
  id("java")
  kotlin("jvm")
}

dependencies {
  implementation("org.jetbrains:annotations:13.0")
  implementation(project(":chapter4:utils"))
}