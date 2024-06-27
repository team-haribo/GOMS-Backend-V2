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
    implementation(Dependencies.JACKSON_CORE_MODULE)

    /* validation */
    implementation(Dependencies.VALIDATION)

    /* test */
    implementation(Dependencies.SPRING_BOOT_STARTER)
    implementation(Dependencies.MOCKITOKOTLIN2)

}

tasks.getByName<Jar>("bootJar") {
    enabled = false
}