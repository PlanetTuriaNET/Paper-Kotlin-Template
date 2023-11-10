plugins {
    kotlin("jvm") version "1.9.20"
    id("io.papermc.paperweight.userdev") version "1.5.8"
    id("xyz.jpenilla.run-paper") version "2.2.0"
}

group = "net.planetturia"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    paperweight.paperDevBundle("1.20.2-R0.1-SNAPSHOT")
    implementation("de.miraculixx", "kpaper", "1.1.0")
    implementation("dev.jorel", "commandapi-bukkit-shade", "9.2.0")
    implementation("dev.jorel", "commandapi-bukkit-kotlin", "9.2.0")
    implementation("io.github.crackthecodeabhi", "kreds","0.9.0")
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