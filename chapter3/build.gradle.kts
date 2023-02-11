import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  kotlin("multiplatform")
  kotlin("plugin.serialization")
}

kotlin {
  jvm {
    withJava()
  }
  js(IR) {
    nodejs()
    binaries.executable()
  }

  sourceSets {
    val jvmMain by getting {
      dependencies {
        // 3-15
        implementation("org.jetbrains.kotlin:kotlin-reflect:${Version.kotlin}")

        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
        implementation("com.squareup.moshi:moshi-kotlin:1.13.0")

        implementation("com.google.code.gson:gson:2.9.0")
        implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:${Version.kotlinxMetadataJvm}")
      }
    }
  }
}

tasks.withType<KotlinCompile> {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xcontext-receivers")
  }
}

// 3-11
tasks.withType<JavaCompile> {
  options.compilerArgs.add("-parameters")
}