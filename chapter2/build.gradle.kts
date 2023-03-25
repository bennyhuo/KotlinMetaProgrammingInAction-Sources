plugins {
  id("java")
  kotlin("jvm")
  id("org.jetbrains.dokka")
}

dependencies {
  implementation(kotlin("reflect"))
  implementation(project(":$name:library"))

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")

  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
  implementation("com.squareup.moshi:moshi-kotlin:1.13.0")

  implementation("com.google.code.gson:gson:2.9.0")

  // 2-20
  implementation("org.jetbrains.kotlinx:kotlinx-metadata-jvm:${Version.kotlinxMetadataJvm}")

  // 2-22
  implementation("com.bennyhuo.kotlin:kotlinp:${Version.kotlinp}")

  testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
  useJUnitPlatform()
}

val dokkaHtml by tasks.getting(org.jetbrains.dokka.gradle.DokkaTask::class) {
  dokkaSourceSets {
    configureEach {
      samples.from("src/main/java/com/bennyhuo/list2_3_4/sayHello.kt")
    }
  }
}
