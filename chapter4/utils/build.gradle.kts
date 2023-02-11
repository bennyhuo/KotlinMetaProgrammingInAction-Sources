plugins {
  id("java")
  kotlin("jvm")
}

dependencies {
  implementation("org.jetbrains:annotations:13.0")
  testImplementation(kotlin("test-common"))
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

}

tasks.withType<Jar> {
  destinationDirectory.set(projectDir.resolve("../data"))
}

tasks.getByName<Test>("test") {
  useJUnitPlatform()
}