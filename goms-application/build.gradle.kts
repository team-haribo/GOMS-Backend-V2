plugins {
    id("org.springframework.boot") version PluginVersions.SPRING_VERSION
    id("io.spring.dependency-management") version PluginVersions.DEPENDENCY_MANAGER_VERSION
    kotlin("plugin.allopen") version PluginVersions.ALL_OPEN_VERSION
    kotlin("plugin.spring") version PluginVersions.SPRING_PLUGIN_VERSION
}

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

springBoot {
    mainClass.set("com.goms.v2.GomsV2ApplicationKt")
}

dependencies {
    /* impl */
    implementation(project(":goms-domain"))

    /* gauth */
    implementation(Dependencies.GAUTH)

    /* spring */
    implementation(Dependencies.TRANSACTION)
    implementation(Dependencies.SPRING_WEB)

    /* mapstruct */
    implementation(Dependencies.MAP_STRUCT)
    kapt(Dependencies.MAP_STRUCT_PROCESSOR)
    kaptTest(Dependencies.MAP_STRUCT_PROCESSOR)
}