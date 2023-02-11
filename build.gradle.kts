plugins {
  kotlin("jvm") version Version.kotlin apply false
  kotlin("android") version Version.kotlin apply false
  kotlin("multiplatform") version Version.kotlin apply false
  kotlin("plugin.serialization") version Version.kotlin apply false
  kotlin("plugin.allopen") version Version.kotlin apply false

  id("com.google.devtools.ksp") version Version.ksp apply false
  id("org.jetbrains.dokka") version Version.dokka apply false

  id("com.android.application") version Version.android apply false
  id("com.android.library") version Version.android apply false
}

group = "com.bennyhuo.kotlin"
version = "1.0-SNAPSHOT"

allprojects {
  repositories {
    maven("https://mirrors.tencent.com/nexus/repository/maven-public/")
    mavenCentral()

    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
  }
}
