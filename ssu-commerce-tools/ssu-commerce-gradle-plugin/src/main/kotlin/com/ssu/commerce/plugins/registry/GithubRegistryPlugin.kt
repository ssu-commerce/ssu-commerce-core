package com.ssu.commerce.plugins.registry

import com.ssu.commerce.plugins.util.findToken
import com.ssu.commerce.plugins.util.findUserName
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.repositories
import java.net.URI

class GithubRegistryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        target.repositories {
            mavenCentral()
            maven {
                name = "GitHubPackages"
                url = URI("https://maven.pkg.github.com/ssu-commerce/ssu-commerce-core")
                credentials {
                    username = target.findUserName()
                    password = target.findToken()
                }
            }
        }
    }
}