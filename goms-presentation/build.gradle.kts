plugins {
    id("org.springframework.boot") version PluginVersions.SPRING_BOOT_VERSION
    id("io.spring.dependency-management") version PluginVersions.DEPENDENCY_MANAGER_VERSION
    kotlin("plugin.spring") version PluginVersions.SPRING_PLUGIN_VERSION
    id("jacoco")
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

jacoco {
    toolVersion = "0.8.10"
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport) // 테스트 후에 JaCoCo 리포트 생성

    jacoco {
        isEnabled = true // JaCoCo가 활성화되어 있는지 확인
    }
}

tasks.jacocoTestReport {
    dependsOn(tasks.test) // 테스트 후에 JaCoCo 리포트 생성

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    classDirectories.setFrom(
        files(classDirectories.files.map {
            fileTree(it) {
                exclude("**/dto/**")
                exclude("**/mapper/**")
            }
        })
    )
}

tasks.getByName<Jar>("bootJar") {
    enabled = false
}
