import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  java
  kotlin("jvm")
}

dependencies {
  implementation(kotlin("stdlib"))
  implementation(project(":chapter5:sample-annotations"))
  implementation("com.google.devtools.ksp:symbol-processing-api:1.6.21-1.0.5")
  implementation("com.squareup:kotlinpoet-ksp:1.12.0")

  testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.withType<KotlinCompile>().configureEach {
  kotlinOptions {
    freeCompilerArgs = listOf(
      "-Xopt-in=com.squareup.kotlinpoet.ksp.KotlinPoetKspPreview",
      "-Xopt-in=com.google.devtools.ksp.KspExperimental"
    )
  }
}