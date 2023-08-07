plugins {
    id("org.springframework.boot") version PluginVersions.SPRING_BOOT_VERSION
    id("io.spring.dependency-management") version PluginVersions.DEPENDENCY_MANAGER_VERSION
    kotlin("plugin.spring") version PluginVersions.SPRING_PLUGIN_VERSION
}

repositories {
    mavenCentral()
}

dependencies {
    // impl project
    implementation(project(":goms-application"))
    implementation(project(":goms-domain"))

    /* web */
    implementation(Dependencies.SPRING_WEB)

    /* kotlin */
    implementation(Dependencies.JACKSON_MODULE)

    /* validation */
    implementation(Dependencies.VALIDATION)

    /* mapstruct */
    implementation(Dependencies.MAP_STRUCT)
    kapt(Dependencies.MAP_STRUCT_PROCESSOR)
    kaptTest(Dependencies.MAP_STRUCT_PROCESSOR)
}

tasks.getByName<Jar>("bootJar") {
    enabled = false
}