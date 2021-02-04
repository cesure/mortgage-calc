import org.jetbrains.kotlin.cli.common.toBooleanLenient
import org.jetbrains.kotlin.gradle.targets.js.NpmVersions
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig.DevServer

plugins {
    application
    id("dev.fritz2.fritz2-gradle") version Versions.Plugin.fritz2
    kotlin("plugin.serialization") version Versions.Plugin.kotlin
    id("io.gitlab.arturbosch.detekt") version Versions.Plugin.detekt
}

group = "com.github.cesure"
version = "1.0"

repositories {
    jcenter()
    maven(url = "https://kotlin.bintray.com/kotlinx/")
}

detekt {
    config = files("${projectDir}/.detekt/config.yml")
    input = files("src/commonMain/kotlin", "src/jsMain/kotlin", "src/jvmMain/kotlin")
    buildUponDefaultConfig = true
}

kotlin {
    js(LEGACY) {
        browser {
            runTask {
                devServer = DevServer(
                    port = 9000,
                    contentBase = listOf("$projectDir/src/jsMain/resources"),
                    proxy = mapOf("/api" to "http://localhost:8080")
                )
            }
        }
    }

    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
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

val mortgagecalcUseNvm: String? by project

// use Node.js from nvm when `mortgagecalcUseNvm=true` is set in gradle.properties
rootProject.plugins.withType(NodeJsRootPlugin::class.java) {
    if (mortgagecalcUseNvm.toBooleanLenient() == true) {
        val nodeBinary = File(System.getProperty("user.home"), ".nvm/versions/node/v${Versions.Plugin.node}/bin/node")
        if (!nodeBinary.isFile) {
            throw GradleException("Cannot find node binary at $nodeBinary")
        }

        rootProject.the<NodeJsRootExtension>().apply {
            download = false
            nodeVersion = Versions.Plugin.node
            nodeCommand = nodeBinary.toString()
        }
    }
}
