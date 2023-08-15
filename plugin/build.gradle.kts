plugins {
    id("maven-publish")
    `kotlin-dsl`
}

group = "com.ssu.commerce"
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
        register<MavenPublication>("plugin") {
            artifactId = "plugin"
            from(components["java"])
        }
    }
}
