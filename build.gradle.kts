plugins {
    kotlin("jvm") version "1.9.23"
    id("com.github.johnrengelman.shadow" ) version "8.1.1"
}

group = "cn.trollaura"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
        maven {
            url = uri("https://jitpack.io")
        }


}

dependencies {
    implementation("mysql:mysql-connector-java:8.0.28")
    implementation("com.github.SNWCreations:JKook:0.52.1")
    implementation("org.json:json:20240303")
}

kotlin {
    jvmToolchain(8)
}

tasks.shadowJar{
    archiveClassifier.set("all")
}
tasks.build {
    dependsOn("shadowJar")
}



