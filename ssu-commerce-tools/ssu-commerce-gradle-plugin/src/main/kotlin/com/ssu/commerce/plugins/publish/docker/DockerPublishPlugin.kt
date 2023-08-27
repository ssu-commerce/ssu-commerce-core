package com.ssu.commerce.plugins.publish.docker

import com.google.cloud.tools.jib.gradle.JibExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class DockerPublishPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.pluginManager.apply("com.google.cloud.tools.jib")

        target.extensions.getByType(JibExtension::class.java)
            .apply {
                from {
                    image = "adoptopenjdk/openjdk11"
                }
                to {
                    // image 이름
                    image = "${target.name}-${target.version.toString().toLowerCase()}"

                    // image tag는 여러 개 적을 수 있다.
                    tags = setOf("latestungae")
                }
                container {
                    creationTime.set("USE_CURRENT_TIMESTAMP")
                    // JVM 옵션들
                    // 서버 포트로는 8080을 사용했으며, profile로는 local을 선택했다.

                    jvmFlags = listOf(
                        "-Dspring.profiles.active=local",
                        "-XX:+UseContainerSupport",
                        "-Dserver.port=8080",
                        "-Dfile.encoding=UTF-8"
                    )

                    // 컨테이너가 외부로 노출할 포트이다.
                    ports = listOf("8080")
                }
            }
    }
}