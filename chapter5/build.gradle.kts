plugins {
  id("java")
  kotlin("jvm")
  kotlin("kapt")
  kotlin("plugin.serialization")
  id("com.google.devtools.ksp")
}

dependencies {
  implementation(kotlin("reflect"))

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
  implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")

  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
  implementation("com.squareup.moshi:moshi-kotlin:1.13.0")

  implementation("com.google.code.gson:gson:2.9.0")

  implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.4.2")

  kapt(project(":$name:sample-apt"))
  ksp(project(":$name:sample-ksp"))

  implementation(project(":$name:sample-annotations"))

  testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

// 5-8
kapt {
  arguments {
    arg("optionA", "A")
    arg("optionB", "B")
    arg("optionC", "C")
  }
}

kotlin {
  sourceSets.main {
    kotlin.srcDir("build/generated/ksp/main/kotlin")
  }
  sourceSets.test {
    kotlin.srcDir("build/generated/ksp/test/kotlin")
  }
}

tasks.getByName<Test>("test") {
  useJUnitPlatform()
}