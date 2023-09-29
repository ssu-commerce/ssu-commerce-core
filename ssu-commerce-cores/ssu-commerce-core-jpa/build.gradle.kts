plugins {
    kotlin("plugin.jpa") version "1.6.10"
}

dependencies {
    api("com.ssu.commerce:vault:2023.07.2")
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.h2database:h2")
    runtimeOnly("mysql:mysql-connector-java")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-security")

    // Document
    api("org.springdoc:springdoc-openapi-ui:1.6.8")
}
