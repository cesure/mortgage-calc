import org.jetbrains.kotlin.gradle.targets.js.NpmVersions

plugins {
    kotlin("multiplatform") version Versions.Plugin.kotlin
    kotlin("plugin.serialization") version Versions.Plugin.kotlin
    id("dev.fritz2.fritz2-gradle") version Versions.Plugin.fritz2
}

repositories {
    jcenter()
    maven(url = "https://kotlin.bintray.com/kotlinx/")
}

kotlin {
    jvm {
    }

    js(IR) {
        browser {
            binaries.executable()
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:${Versions.Dependency.kotlinxDatetime}")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.Dependency.kotlinxSerializationJson}")
            }
        }
        val jvmMain by getting {
            dependencies {
            }
        }
        val jsMain by getting {
            dependencies {
                val npmVersions = NpmVersions()
                implementation(npm("big.js", "^5.2.2"))
                implementation(devNpm("autoprefixer", "^10.1.0"))
                implementation(devNpm("css-loader", npmVersions.cssLoader.version))
                implementation(devNpm("postcss", "^8.2.2"))
                implementation(devNpm("postcss-import", "^14.0.0"))
                implementation(devNpm("postcss-loader", "^4.1.0"))
                implementation(devNpm("style-loader", npmVersions.styleLoader.version))
                implementation(devNpm("tailwindcss", "^2.0.2"))
            }
        }
    }
}
