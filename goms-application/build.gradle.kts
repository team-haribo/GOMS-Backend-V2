plugins {
    kotlin("plugin.allopen") version PluginVersions.ALL_OPEN_VERSION
    id("jacoco")
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

jacoco {
    toolVersion = "0.8.12"
}

tasks.test {
    finalizedBy("jacocoTestReport") // 테스트 후에 JaCoCo 리포트 생성

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
                exclude("**/common/**")
                exclude("**/data/**")
                exclude("**/exception/**")
                exclude("**/scheduler/**")
                exclude("**/spi/**")
                exclude("**/event/**")
            }
        })
    )
}
