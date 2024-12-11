plugins {
    id("org.springframework.boot") version PluginVersions.SPRING_BOOT_VERSION
    id("io.spring.dependency-management") version PluginVersions.DEPENDENCY_MANAGER_VERSION
    kotlin("plugin.jpa") version PluginVersions.JPA_PLUGIN_VERSION
    kotlin("plugin.spring") version PluginVersions.SPRING_PLUGIN_VERSION
    kotlin("plugin.allopen") version PluginVersions.ALL_OPEN_VERSION
}

repositories {
    mavenCentral()
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    /* impl */
    implementation(project(":goms-domain"))
    implementation(project(":goms-application"))
    implementation(project(":goms-presentation"))

    /* spring */
    implementation(Dependencies.SPRING_WEB)

    /* jpa */
    implementation(Dependencies.SPRING_DATA_JPA)

    /* aws */
    implementation(Dependencies.AWS_STARTER)


    /* queryDsl */
    implementation(Dependencies.QUERYDSL)
    kapt(Dependencies.QUERYDSL_PROCESSOR)

    // kotlin
    implementation(Dependencies.KOTLIN_JACKSON)
    implementation(Dependencies.KOTLIN_REFLECT)
    implementation(Dependencies.KOTLIN_STDLIB)


    /* email */
    implementation(Dependencies.EMAIL)

    /* redis */
    implementation(Dependencies.REDIS)

    /* jwt */
    implementation(Dependencies.JWT_API)
    runtimeOnly(Dependencies.JWT_IMPL)
    runtimeOnly(Dependencies.JWT_JACKSON)

    // security
    implementation(Dependencies.SPRING_SECURITY)

    /* database */
    runtimeOnly(Dependencies.MYSQL_CONNECTOR)
    implementation(Dependencies.REDIS)
    implementation(Dependencies.SPRING_REDIS)
    implementation(Dependencies.MARIA_DB)

    /* fcm */
    implementation(Dependencies.FCM)
    implementation(Dependencies.APACHE_API_CLIENT)
    implementation(Dependencies.APACHE_HTTP_2)
    implementation(Dependencies.APACHE_HTTP)

    /* monitoring */
    implementation(Dependencies.ACTUATOR)
    runtimeOnly(Dependencies.PROMETHEUS)

    /* thymeleaf */
    implementation(Dependencies.THYMELEAF)
    implementation(kotlin("stdlib-jdk8"))

}

allOpen {
    annotation(AllOpen.ENTITY)
    annotation(AllOpen.MAPPED_SUPERCLASS)
    annotation(AllOpen.EMBEDDABLE)
    annotation(AllOpen.CONFIGURATION)
}

noArg {
    annotation(AllOpen.ENTITY)
    annotation(AllOpen.MAPPED_SUPERCLASS)
    annotation(AllOpen.EMBEDDABLE)
}

tasks.getByName<Jar>("jar") {
    enabled = false
}
