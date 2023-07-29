plugins {
    id("org.springframework.boot") version PluginVersions.SPRING_VERSION
    id("io.spring.dependency-management") version PluginVersions.DEPENDENCY_MANAGER_VERSION
    kotlin("plugin.spring") version PluginVersions.SPRING_PLUGIN_VERSION
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {

    // impl project
    implementation(project(":goms-application"))
    implementation(project(":goms-domain"))

    /* web */
    implementation(Dependencies.SPRING_WEB)

    /* validation */
    implementation(Dependencies.VALIDATION)

}

tasks.getByName<Jar>("bootJar") {
    enabled = false
}