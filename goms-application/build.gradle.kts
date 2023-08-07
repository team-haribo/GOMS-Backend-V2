repositories {
    mavenCentral()
}

dependencies {
    /* impl */
    implementation(project(":goms-domain"))

    /* spring */
    implementation(Dependencies.TRANSACTION)

    /* mapstruct */
    implementation(Dependencies.MAP_STRUCT)
    kapt(Dependencies.MAP_STRUCT_PROCESSOR)
    kaptTest(Dependencies.MAP_STRUCT_PROCESSOR)
}