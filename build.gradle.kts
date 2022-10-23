import org.jetbrains.compose.jetbrainsCompose

plugins {
    kotlin("multiplatform") version "1.7.10"
    application
    id("org.jetbrains.compose") version "1.2.0-alpha01-dev770"
    kotlin("plugin.serialization") version "1.6.10"
}

group = "io.starlight220"
version = "1.0"

repositories {
    mavenCentral()
    jetbrainsCompose()
    maven("https://androidx.dev/storage/compose-compiler/repository/")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {

                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.0")

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(compose.foundation)
                implementation(compose.material)
                // Needed only for preview.
                implementation(compose.preview)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.common)
                implementation(compose.desktop.currentOs)
                implementation(compose.ui)
            }
        }
    }
}

compose {
    desktop {}
}

application {
    mainClass.set("io.starlight220.editrtl.MainKt")
}

tasks.withType<Jar> {
    // Otherwise you'll get a "No main manifest attribute" error
    manifest {
        attributes["Main-Class"] = "io.starlight220.editrtl.MainKt"
    }
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    val main by kotlin.jvm().compilations.getting
    from({
        main.runtimeDependencyFiles.files.filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}
