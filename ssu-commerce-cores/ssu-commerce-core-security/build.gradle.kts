plugins {
    kotlin("plugin.jpa") version "1.6.10"
}

dependencies {
    api("org.springframework.boot:spring-boot-starter-web")
    api("com.ssu.commerce:vault:2023.07.2")
    // Security
    api("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.data:spring-data-commons")
    implementation("io.jsonwebtoken:jjwt-api:0.11.2")
    implementation("io.jsonwebtoken:jjwt-impl:0.11.2")
    implementation("io.jsonwebtoken:jjwt-jackson:0.11.2")

    testImplementation(project(":ssu-commerce-cores:ssu-commerce-core-jpa"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-starter-security")
}
