plugins {
    kotlin("jvm") version "1.3.72"
    application
}

group = "com.github.ascii"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

val scrimageVersion = "4.0.5"

dependencies {
    implementation(kotlin("stdlib-jdk8"))

    implementation("com.github.ajalt:clikt:2.7.1")
    implementation("com.sksamuel.scrimage:scrimage-core:$scrimageVersion")
    implementation("com.sksamuel.scrimage:scrimage-filters:$scrimageVersion")
}

application {
    mainClassName = "com.github.ascii.AsciiKt"
//    mainClass.set("org.gradle.sample.Main")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}