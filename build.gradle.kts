import org.jetbrains.kotlin.gradle.targets.js.NpmVersions

plugins {
    kotlin("multiplatform") version "1.4.21"
    kotlin("plugin.serialization") version "1.4.21"
    id("dev.fritz2.fritz2-gradle") version "0.8"
}

repositories {
    jcenter()
    maven(url = "https://kotlin.bintray.com/kotlinx/")
}

kotlin {
    jvm {
    }

    js {
        browser {
            binaries.executable()
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.1.1")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.0.1")
            }
        }
        val jvmMain by getting {
            dependencies {
            }
        }
        val jsMain by getting {
            dependencies {
                val npmVersions = NpmVersions()
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
