import org.jetbrains.kotlin.gradle.targets.js.NpmVersions
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.DevServer

plugins {
    application
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
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }

    js {
        browser {
            binaries.executable()

            runTask {
                devServer = DevServer(
                    port = 9000,
                    contentBase = listOf("$projectDir/src/jsMain/resources"),
                    proxy = mapOf("/api" to "http://localhost:8080")
                )
            }
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
                implementation("io.ktor:ktor-server-netty:${Versions.Dependency.ktor}")
                implementation("io.ktor:ktor-serialization:${Versions.Dependency.ktor}")
                implementation("ch.qos.logback:logback-classic:${Versions.Dependency.logback}")
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
        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}

application {
    mainClass.set("com.github.cesure.mortgagecalc.ServerKt")
}
