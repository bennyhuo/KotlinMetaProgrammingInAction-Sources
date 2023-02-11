import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  id("java")
  kotlin("jvm")
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = freeCompilerArgs + listOf("-Xopt-in=kotlin.contracts.ExperimentalContracts")
  }
}

dependencies {
  implementation("org.jetbrains:annotations:13.0")
}

// 2-31
// tasks.withType<Jar> {
//   exclude("**/*.kotlin_module")
// }
