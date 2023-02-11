//enableFeaturePreview("VERSION_CATALOGS")

pluginManagement {
  repositories {
    mavenLocal()
    maven("https://mirrors.tencent.com/nexus/repository/maven-public/")
    gradlePluginPortal()

    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
  }

  resolutionStrategy {
    eachPlugin {
      val module = when(requested.id.id) {
        "kotlinx-atomicfu" -> "org.jetbrains.kotlinx:atomicfu-gradle-plugin:${requested.version}"
        else -> null
      }
      if(module != null) {
        useModule(module)
      }
    }
  }
}

rootDir.walkTopDown().maxDepth(2).filter {
  it.isDirectory && File(it, "build.gradle.kts").exists() && it.name != "buildSrc"
}.forEach {
  include(":" + it.toRelativeString(rootDir).replace(File.separator, ":"))
}

val local = file("composite_build.local")
if (local.exists()) {
  local.readLines().forEach {
    val f = file("../$it")
    if (f.exists()) {
      includeBuild(f)
    }
  }
}
include(":android-app")
