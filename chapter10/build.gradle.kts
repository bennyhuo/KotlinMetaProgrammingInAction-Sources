plugins {
  kotlin("multiplatform")
  id("kotlinx-atomicfu") version "0.18.3"
}

atomicfu {
  dependenciesVersion = "0.18.3" // set to null to turn-off auto dependencies
  transformJvm = true // set to false to turn off JVM transformation
  jvmVariant = "BOTH" // JVM transformation variant: FU,VH, or BOTH
  verbose = true // set to true to be more verbose
}

kotlin {
  jvm()
  js(LEGACY) {
    nodejs()
  }

  sourceSets {
    val jvmMain by getting {
      dependencies {
        implementation("org.openjdk.jol:jol-core:0.10")
      }
    }
  }
}