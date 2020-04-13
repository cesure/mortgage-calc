plugins {
    application
    kotlin("jvm") version Versions.Plugin.kotlin
    id("com.github.johnrengelman.shadow") version Versions.Plugin.shadow
}

application {
    mainClassName = "io.ktor.server.netty.EngineMain"
}

repositories {
    maven { url = uri("https://kotlin.bintray.com/ktor") }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.Dependency.kotlin}")
    implementation("io.ktor:ktor-server-netty:${Versions.Dependency.ktor}")
    implementation("ch.qos.logback:logback-classic:${Versions.Dependency.logback}")
    implementation("io.ktor:ktor-server-core:${Versions.Dependency.ktor}")
    implementation("io.ktor:ktor-auth:${Versions.Dependency.ktor}")
    implementation("io.ktor:ktor-auth-jwt:${Versions.Dependency.ktor}")
    implementation("io.ktor:ktor-jackson:${Versions.Dependency.ktor}")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:${Versions.Dependency.jackson}")
    testImplementation("io.ktor:ktor-server-tests:${Versions.Dependency.ktor}")
    runtimeOnly(project(path = ":frontend", configuration = "frontendResources"))
}

kotlin.sourceSets["main"].kotlin.srcDirs("src")
kotlin.sourceSets["test"].kotlin.srcDirs("test")

sourceSets["main"].resources.srcDirs("resources")
sourceSets["test"].resources.srcDirs("testresources")

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "11"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "11"
    }
}

tasks.withType<Jar> {
    archiveBaseName.set("mortgage-calc")
    manifest {
        attributes(
            mapOf(
                "Main-Class" to application.mainClassName
            )
        )
    }
}
