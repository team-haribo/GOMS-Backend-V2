plugins {
    id("org.springframework.boot") version PluginVersions.SPRING_VERSION
    id("io.spring.dependency-management") version PluginVersions.DEPENDENCY_MANAGER_VERSION
    kotlin("plugin.jpa") version PluginVersions.JPA_PLUGIN_VERSION
    kotlin("plugin.spring") version PluginVersions.SPRING_PLUGIN_VERSION
    kotlin("plugin.allopen") version PluginVersions.ALL_OPEN_VERSION
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":goms-application"))
    implementation(project(":goms-presentation"))
    implementation(project(":goms-domain"))
}

allOpen {
    annotation(AllOpen.ENTITY)
    annotation(AllOpen.MAPPED_SUPERCLASS)
    annotation(AllOpen.EMBEDDABLE)
}

tasks.getByName<Jar>("bootJar") {
    enabled = false
}