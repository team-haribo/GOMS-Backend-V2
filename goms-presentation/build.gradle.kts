plugins {
    id("org.springframework.boot") version PluginVersions.SPRING_VERSION
    id("io.spring.dependency-management") version PluginVersions.DEPENDENCY_MANAGER_VERSION
    kotlin("plugin.spring") version PluginVersions.SPRING_PLUGIN_VERSION
}

repositories {
    mavenCentral()
}

dependencies {
    /* web */
    implementation(Dependencies.SPRING_WEB)
}

tasks.getByName<Jar>("bootJar") {
    enabled = false
}