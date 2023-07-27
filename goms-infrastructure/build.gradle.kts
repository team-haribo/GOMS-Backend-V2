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
    /* impl */
    implementation(project(":goms-domain"))

    /* spring */
    implementation(Dependencies.SPRING_WEB)

    /* jpa */
    implementation(Dependencies.SPRING_DATA_JPA)

    /* queryDsl */
    implementation(Dependencies.QUERYDSL)
    kapt(Dependencies.QUERYDSL_PROCESSOR)

    /* database */
    runtimeOnly(Dependencies.MYSQL_CONNECTOR)
    implementation(Dependencies.REDIS)
    implementation(Dependencies.SPRING_REDIS)

    /* mapstruct */
    implementation(Dependencies.MAP_STRUCT)
    kapt(Dependencies.MAP_STRUCT_PROCESSOR)
    kaptTest(Dependencies.MAP_STRUCT_PROCESSOR)
}

allOpen {
    annotation(AllOpen.ENTITY)
    annotation(AllOpen.MAPPED_SUPERCLASS)
    annotation(AllOpen.EMBEDDABLE)
}

noArg {
    annotation(AllOpen.ENTITY)
    annotation(AllOpen.MAPPED_SUPERCLASS)
    annotation(AllOpen.EMBEDDABLE)
}

kapt {
    arguments {
        arg("mapstruct.defaultComponentModel", "spring")
        arg("mapstruct.unmappedTargetPolicy", "ignore")
    }
}

tasks.getByName<Jar>("bootJar") {
    enabled = false
}