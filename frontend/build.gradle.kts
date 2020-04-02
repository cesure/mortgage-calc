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

tasks.named<NpmTask>("npm_run_build") {
    inputs.files(fileTree("public"))
    inputs.files(fileTree("src"))

    inputs.file("package.json")
    inputs.file("package-lock.json")

    outputs.dir("dist")
}

val packageFrontend by tasks.registering(Jar::class) {
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
    add(frontendResources.name, packageFrontend.get().archiveFile) {
        builtBy(packageFrontend)
        type = "jar"
    }
}

tasks.assemble {
    dependsOn(packageFrontend)
}

tasks.clean {
    delete(packageFrontend.get().archiveFile)
}
