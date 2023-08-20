plugins {
    `maven-publish`
    `java-gradle-plugin`
    `kotlin-dsl`
}

group = "com.ssu.commerce.plugin"
version = System.getenv("VERSION")

repositories {
    mavenCentral()
    gradlePluginPortal()
}

fun findUserName() = (project.findProperty("gpr.user") as String?).nullWhenEmpty() ?: System.getenv("USERNAME")
fun findToken() = (project.findProperty("gpr.key") as String?).nullWhenEmpty() ?: System.getenv("TOKEN")

fun String?.nullWhenEmpty() = if (this.isNullOrEmpty()) null else this

dependencies {
    implementation("com.google.cloud.tools:jib-gradle-plugin:3.3.1")
    api("com.fasterxml.jackson.core:jackson-databind:2.13.2")
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
        }
    }
}

gradlePlugin {
    plugins {
        create("maven-publish"){
            id = "com.ssu.commerce.plugin.maven-publish"
            displayName = "ssu-commerce maven publish plugin"
            description = "maven publish for ssu-commerce project"
            implementationClass = "com.ssu.commerce.plugins.publish.maven.MavenPublishPlugin"
        }
    }

    plugins {
        create("docker-publish"){
            id = "com.ssu.commerce.plugin.docker-publish"
            displayName = "ssu-commerce docker publish plugin"
            description = "docker publish for ssu-commerce project"
            implementationClass = "com.ssu.commerce.plugins.publish.docker.DockerPublishPlugin"
        }
    }
}