import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    kotlin("jvm") version "1.6.0"
}

group = "be.tabs_spaces"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("be.tabs_spaces.advent2021.Runner")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-simple:1.7.9")
    implementation("org.reflections:reflections:0.10.2")

    testImplementation("org.assertj:assertj-core:3.21.0")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}