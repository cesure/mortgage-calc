import com.moowork.gradle.node.npm.*

plugins {
    base
    id("com.github.node-gradle.node") version Versions.Plugin.nodeGradle
}

node {
    version = Versions.Dependency.node
    download = true
}

tasks.create<NpmTask>("serve") {
    dependsOn("npmInstall")
    setArgs(listOf("run", "serve"))
}
