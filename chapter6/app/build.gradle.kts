plugins {
  id("java")
  kotlin("jvm")
  id("io.gitlab.arturbosch.detekt") version Version.detekt
}

dependencies {
  detektPlugins(project(":chapter6:dataclass-detekt-plugin"))
  implementation("org.jetbrains:annotations:13.0")
}

val parentProject = parent!!.name

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
  this.jvmTarget = "1.8"
  dependsOn(":${parentProject}:dataclass-detekt-plugin:assemble")
}
tasks.withType<io.gitlab.arturbosch.detekt.DetektCreateBaselineTask>().configureEach {
  this.jvmTarget = "1.8"
  dependsOn(":${parentProject}:dataclass-detekt-plugin:assemble")
}

detekt {
  config.from("config/detekt.yaml")
}