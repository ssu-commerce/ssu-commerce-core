package com.ssu.commerce.plugins.publish.docker

import com.google.cloud.tools.jib.gradle.JibExtension
import com.ssu.commerce.plugins.util.findToken
import com.ssu.commerce.plugins.util.findUserName
import com.ssu.commerce.plugins.util.nullWhenEmpty
import org.gradle.api.Plugin
import org.gradle.api.Project

class DockerPublishPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("com.google.cloud.tools.jib")
        val path = (target.findProperty("docker-path") as String?).nullWhenEmpty() ?: "ghcr.io/ssu-commerce"
        target.gradle.projectsEvaluated {
            target.extensions.getByType(JibExtension::class.java).apply {
                from {
                    image = "adoptopenjdk/openjdk11"
                }
                to {
                    // image 이름
                    image = "$path/${target.name}"
                    tags = setOf("${target.version}", "latest")
                    // image tag는 여러 개 적을 수 있다.
                    auth {
                        username = target.findUserName()
                        password = target.findToken()
                    }
                }
                container {
                    creationTime.set("USE_CURRENT_TIMESTAMP")
                    // JVM 옵션들
                    // 서버 포트로는 8080을 사용했으며, profile로는 local을 선택했다.
                    jvmFlags = listOf(
                        "-XX:+UseContainerSupport",
                        "-Dfile.encoding=UTF-8"
                    )
                }
            }
        }

        target.tasks.findByName("jib")?.doLast { println("$path/${target.name}:${target.version} upload Completed") }
    }
}