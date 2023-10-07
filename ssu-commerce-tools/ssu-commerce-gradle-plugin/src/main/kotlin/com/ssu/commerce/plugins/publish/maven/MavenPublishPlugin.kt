package com.ssu.commerce.plugins.publish.maven

import com.ssu.commerce.plugins.util.findToken
import com.ssu.commerce.plugins.util.findUserName
import com.ssu.commerce.plugins.util.nullWhenEmpty
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.jvm.tasks.Jar
import java.net.URI

class MavenPublishPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("maven-publish")
        target.pluginManager.apply("java")

        target.extensions.getByType(JavaPluginExtension::class.java).apply {
            sourceCompatibility = JavaVersion.VERSION_11
            withJavadocJar()
            withSourcesJar()
        }

        target.tasks.withType(Jar::class.java) {
            archiveClassifier.set("")
        }

        target.extensions.getByType(PublishingExtension::class.java).apply {
            val repositoryName: String =
                (target.findProperty("maven-repository-name") as String?).nullWhenEmpty() ?: "ssu-commerce-core"
            val repositoryURI: String = (target.findProperty("maven-repository") as String?).nullWhenEmpty()
                ?: "https://maven.pkg.github.com/ssu-commerce/ssu-commerce-core"

            repositories {
                maven {
                    name = repositoryName
                    url = URI(repositoryURI)
                    credentials {
                        username = target.findUserName()
                        password = target.findToken()
                    }
                }

            }
            publications {
                register(target.name, MavenPublication::class.java) {
                    var jar: Jar? = target.tasks.withType(Jar::class.java).findByName("jar")

                    if (jar?.enabled == true) {
                        from(target.components.getByName("java"))
                    }
                }
            }
        }

        target.tasks.findByName("publish")
            ?.doLast { println("\n${target.group}:${target.name}:${target.version} upload Completed") }
    }
}
