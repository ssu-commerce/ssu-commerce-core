dependencies {
    // HTTP
    api("org.springframework.cloud:spring-cloud-starter-openfeign:3.1.3")
}

subprojects {
    dependencies {
        api(project(":ssu-commerce-cores:ssu-commerce-core-io"))
    }
}
