plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.20"
    `java-library`
    `maven-publish`
}

group = "ai.springchain"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework:spring-webflux:6.0.10")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.1")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
    implementation("io.github.microutils:kotlin-logging-jvm:3.0.5")
    implementation("ch.qos.logback:logback-classic:1.4.8")

    testImplementation("io.kotest:kotest-runner-junit5-jvm:5.6.2")
    testImplementation("io.kotest:kotest-assertions-core-jvm:5.6.2")
    testImplementation("io.kotest:kotest-property-jvm:5.6.2")
    testImplementation("io.mockk:mockk-jvm:1.13.5")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.11.0")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}
repositories {
    mavenCentral()

    maven {
        name = "GithubPackages"
        url = uri("https://maven.pkg.github.com/OWNER/REPOSITORY") // zamień OWNER i REPOSITORY na swoje dane
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/springchain/openaikt")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("TOKEN")
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
        }
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

val newVersion: String? by project
tasks.register("setVersion") {
    doLast {
        val version = newVersion ?: "undefined"
        project.version = version
        println("Version set to ${project.version} hmm $version ")
    }
}
