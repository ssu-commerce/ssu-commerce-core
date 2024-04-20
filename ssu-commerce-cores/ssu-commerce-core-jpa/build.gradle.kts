plugins {
    kotlin("plugin.jpa") version "1.6.10"
    kotlin("kapt")
}

dependencies {
    // spring
    api("org.springframework.boot:spring-boot-starter-data-jpa")

    // querydsl
    api("com.querydsl:querydsl-jpa:5.0.0")
    kapt("com.querydsl:querydsl-apt:5.0.0:jpa")

    // drivers
    runtimeOnly("com.h2database:h2")
    runtimeOnly("mysql:mysql-connector-java")

    // test
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    kaptTest("com.querydsl:querydsl-apt:5.0.0:jpa")
}
