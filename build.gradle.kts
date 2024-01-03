import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    kotlin("jvm") version "1.9.+"
    id("io.papermc.paperweight.userdev") version "1.5.+"
    id("xyz.jpenilla.run-paper") version "2.2.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

val exposedVersion: String by project
val serverVersion: String by project

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("$serverVersion-R0.1-SNAPSHOT")

    // Minecraft
    library("de.miraculixx:kpaper:1.+")
    library("dev.jorel:commandapi-bukkit-shade:9.+")
    library("dev.jorel:commandapi-bukkit-kotlin:9.+")

    // Kotlin
    implementation(kotlin("stdlib"))
    library("org.jetbrains.kotlinx:kotlinx-serialization-json:1.+")
    library("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.+")

    // Database
    library("io.github.crackthecodeabhi", "kreds","0.9.1")
}

bukkit {

    name = properties["name"] as String
    author = properties["author"] as String
    description = properties["description"] as String
    website = properties["website"] as String

    group = properties["group"] as String
    main = "$group.${rootProject.name.lowercase()}.Main"

    version = properties["version"] as String
    apiVersion = "1.20"

    load = BukkitPluginDescription.PluginLoadOrder.STARTUP
    depend = listOf()
    softDepend = listOf()
}

kotlin {
    jvmToolchain(17)
}

tasks {
    assemble {
        dependsOn(reobfJar)
    }
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(17)
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}