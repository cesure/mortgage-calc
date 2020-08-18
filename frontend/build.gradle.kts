import com.moowork.gradle.node.npm.*
import groovy.json.*

plugins {
    base
    id("com.github.node-gradle.node") version Versions.Plugin.nodeGradle
}

node {
    version = Versions.Dependency.node
    download = true
    npmInstallCommand = "ci"
}

tasks.named<NpmTask>("npm_run_build") {
    inputs.files(fileTree("public"))
    inputs.files(fileTree("src"))

    inputs.files("package.json", "package-lock.json")
    inputs.files("tsconfig.json")
    inputs.files("babel.config.js", "postcss.config.js", "tailwind.config.js", "vue.config.js")

    outputs.dir("dist")
}

val assembleFrontend by tasks.registering(Jar::class) {
    dependsOn("npm_run_build")
    archiveBaseName.set("mortgage-calc-frontend")
    archiveExtension.set("jar")
    destinationDirectory.file("${projectDir}/build")
    from("dist") {
        into("static")
    }
}

val frontendResources by configurations.creating

configurations.named("default").get().extendsFrom(frontendResources)

artifacts {
    add(frontendResources.name, assembleFrontend.get().archiveFile) {
        builtBy(assembleFrontend)
        type = "jar"
    }
}

tasks.assemble {
    dependsOn(assembleFrontend)
}

tasks.clean {
    delete(assembleFrontend.get().archiveFile)
}

val checkVersion by tasks.registering {
    val packageJson = File(projectDir, "package.json")
    inputs.files(packageJson)

    val packageJsonMap = JsonSlurper().parseText(packageJson.readText()) as Map<*, *>
    val packageJsonVersion = packageJsonMap["version"] as String
    if (project.version != packageJsonVersion) {
        throw GradleException("Version set in package.json \"$packageJsonVersion\" doesn't match project version \"${project.version}\".")
    }
}

val lint by tasks.named<NpmTask>("npm_run_lint") {
    inputs.files(fileTree("public"))
    inputs.files(fileTree("src"))

    inputs.file("package.json")
    inputs.file("package-lock.json")

    outputs.files(fileTree("src"))
}

tasks.check {
    dependsOn(checkVersion)
    dependsOn(lint)
}
