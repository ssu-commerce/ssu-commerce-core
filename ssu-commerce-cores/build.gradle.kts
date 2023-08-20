import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("maven-publish")
    id("org.jlleitschuh.gradle.ktlint") version "10.2.1"
    id("org.springframework.boot") version "2.6.6"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    kotlin("plugin.jpa") version "1.6.10"
}

group = "com.ssu.commerce"
version = System.getenv("VERSION")
java.sourceCompatibility = JavaVersion.VERSION_11

fun findUserName() = (project.findProperty("gpr.user") as String?).nullWhenEmpty() ?: System.getenv("USERNAME")
fun findToken() = (project.findProperty("gpr.key") as String?).nullWhenEmpty() ?: System.getenv("TOKEN")

fun String?.nullWhenEmpty() = if (this.isNullOrEmpty()) null else this

subprojects {
    apply(plugin = "maven-publish")

    repositories {
        mavenCentral()
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/ssu-commerce/ssu-commerce-core")
            credentials {
                username = findUserName()
                password = findToken()
            }
        }
    }

    dependencies {
    }
}
repositories {
    mavenCentral()
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/ssu-commerce/ssu-commerce-core")
        credentials {
            username = findUserName()
            password = findToken()
        }
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

tasks {
    jar {
        enabled = true
        archiveClassifier.set("")
    }
    bootJar {
        enabled = false
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/ssu-commerce/ssu-commerce-core")
            credentials {
                username = findUserName()
                password = findToken()
            }
        }
    }
    publications {
        register<MavenPublication>("gpr") {
            from(components["java"])
            artifact(sourcesJar)
        }
    }
}
