plugins {
  java
  kotlin("jvm")
}

dependencies {
  implementation(kotlin("stdlib"))
  implementation(project(":chapter5:sample-annotations"))
  implementation("com.google.auto:auto-common:1.2.1")
  implementation("com.squareup:javapoet:1.13.0")

  testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}