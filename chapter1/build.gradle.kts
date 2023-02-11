plugins {
  kotlin("jvm")
}

dependencies {
  implementation("com.squareup.okhttp3:okhttp:4.10.0")
  implementation("com.squareup.moshi:moshi:1.14.0")
  implementation("com.squareup.moshi:moshi-kotlin:1.14.0")

  implementation("com.squareup.retrofit2:retrofit:2.9.0")
  implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
  implementation("com.squareup.moshi:moshi-kotlin:1.13.0")

  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.2")
}