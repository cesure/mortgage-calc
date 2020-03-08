import com.moowork.gradle.node.npm.NpmTask

plugins {
    base
    id("com.github.node-gradle.node") version "2.2.3"
}

node {
    version = "12.16.1"
    download = true
}

tasks.create<NpmTask>("serve") {
    dependsOn("npmInstall")
    setArgs(listOf("run", "serve"))
}
