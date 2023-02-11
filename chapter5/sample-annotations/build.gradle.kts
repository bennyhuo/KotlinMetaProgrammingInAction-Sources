plugins {
  java
  kotlin("jvm")
}

dependencies {
  implementation(kotlin("stdlib"))

  testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}