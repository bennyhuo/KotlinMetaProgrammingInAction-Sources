import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  java
  kotlin("jvm")
  id("org.jetbrains.intellij") version "1.5.2"
}

dependencies {
  implementation(kotlin("stdlib"))

  testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
  version.set("2022.1.1")
  plugins.set(listOf("com.intellij.java", "org.jetbrains.kotlin"))
}
tasks {
  patchPluginXml {
    changeNotes.set(
      """
            Add change notes here.<br>
            <em>most HTML tags may be used</em>        """.trimIndent()
    )
  }

}
tasks.getByName<Test>("test") {
  useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
  kotlinOptions {
    freeCompilerArgs = listOf("-Xjvm-default=enable")
  }
}