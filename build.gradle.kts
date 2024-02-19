import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    kotlin("jvm") version "1.9.+"
    id("io.papermc.paperweight.userdev") version "1.5.+"
    id("xyz.jpenilla.run-paper") version "2.2.2"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"

    id("com.github.johnrengelman.shadow") version "8.1.1"
}

val serverVersion: String by project

val packageString = "${properties["group"] as String}.${rootProject.name.lowercase()}"

repositories {
    mavenCentral()
    maven {
        name = "planetturiaRepoSnapshots"
        url = uri("https://repo.planetturia.net/snapshots")
    }
}

dependencies {
    paperweight.paperDevBundle("$serverVersion-R0.1-SNAPSHOT")

    // Minecraft
    implementation("de.miraculixx:kpaper:1.+")
    library("dev.jorel:commandapi-bukkit-shade:9.3.0")
    library("dev.jorel:commandapi-bukkit-kotlin:9.3.0")

    // Kotlin
    implementation(kotlin("stdlib"))
    library("org.jetbrains.kotlinx:kotlinx-serialization-json:1.+")
    library("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.+")

    // Intern
    implementation("net.planetturia:cirrofy:1.+")

    // Database
    implementation("redis.clients:jedis:5.1.0")
}

bukkit {

    name = properties["name"] as String
    author = properties["author"] as String
    description = properties["description"] as String
    website = properties["website"] as String

    group = properties["group"] as String
    main = "$packageString.Main"

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
    shadowJar {
        minimize()
        relocate("net.planetturia.cirrofy", "$packageString.libs.net.planetturia.cirrofy")
        relocate("de.miraculixx.kpaper", "$packageString.libs.de.miraculixx.kpaper")
        destinationDirectory.set(file("build/libs"))
    }
    compileJava {
        options.encoding = "UTF-8"
        options.release.set(17)
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }
}