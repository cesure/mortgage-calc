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

val npmRunBuild = tasks.getByName("npm_run_build") {
    inputs.files(fileTree("public"))
    inputs.files(fileTree("src"))

    inputs.file("package.json")
    inputs.file("package-lock.json")

    outputs.dir("dist")
}
