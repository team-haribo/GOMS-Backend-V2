plugins {
    kotlin("plugin.allopen") version PluginVersions.ALL_OPEN_VERSION
}

repositories {
    mavenCentral()
}

dependencies {
    /* impl */
    implementation(project(":goms-domain"))

    /* spring */
    implementation(Dependencies.TRANSACTION)

    implementation(Dependencies.SPRING_WEB_VERSION)

    /* mapstruct */
    implementation(Dependencies.MAP_STRUCT)
    kapt(Dependencies.MAP_STRUCT_PROCESSOR)
    kaptTest(Dependencies.MAP_STRUCT_PROCESSOR)
}

allOpen {
    annotation(AllOpen.USECASE_WIHT_TRANSACTION)
    annotation(AllOpen.USECASE_WIHT_READONLY_TRANSACTION)
}