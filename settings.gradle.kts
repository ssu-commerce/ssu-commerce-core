pluginManagement {
    fun String?.nullWhenEmpty() = if (this.isNullOrEmpty()) null else this

    fun findUserName() = (settings.extra["gpr.user"] as String?).nullWhenEmpty() ?: System.getenv("USERNAME")
    fun findToken() = (settings.extra["gpr.key"] as String?).nullWhenEmpty() ?: System.getenv("TOKEN")

    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/ssu-commerce/ssu-commerce-core")
            credentials {
                username = findUserName()
                password = findToken()
            }
        }
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.ssu.commerce.plugin") {
                useModule("com.ssu.commerce:plugin:${requested.version}")
            }
        }
    }
}
rootProject.name = "ssu-commerce-core"

include("core")
include("plugin")