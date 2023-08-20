package com.ssu.commerce.plugins.publish.maven

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.jvm.tasks.Jar
import java.net.URI

class MavenPublishPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("maven-publish")

        target.extensions.getByType(PublishingExtension::class.java)
            .apply {
                repositories {
                    maven {
                        name = "GithubPackages"
                        url = URI("https://maven.pkg.github.com/ssu-commerce/ssu-commerce-core")
                        credentials {
                            username = (target.findProperty("gpr.user") as String?).nullWhenEmpty()
                                ?: System.getenv("USERNAME")
                            password =
                                (target.findProperty("gpr.key") as String?).nullWhenEmpty() ?: System.getenv("TOKEN")
                        }
                    }

                }
                publications {
                    register(target.name, MavenPublication::class.java) {
                        val group: String? = (target.properties["group"] as String?) ?: "com.ssu.commerce"
                        val version: String? = (target.properties["version"] as String?) ?: "test"
                        println(group)
                        println(version)
                        groupId = target.group.toString()
                        val jar = target.tasks.withType(Jar::class.java).findByName("jar")
                        if (jar?.enabled == true)
                            artifact(target.tasks.named("jar"))
                    }
                }
            }
    }
}

fun String?.nullWhenEmpty() = if (this.isNullOrEmpty()) null else this
