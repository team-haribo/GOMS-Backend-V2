object Dependencies {
    /* kotlin */
    const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect"
    const val KOTLIN_JDK = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"
    const val JACKSON_MODULE = "com.fasterxml.jackson.module:jackson-module-kotlin"
    const val JACKSON_CORE_MODULE = "com.fasterxml.jackson.core:jackson-annotations:${DependenciesVersions.JACKSON_CORE}"

    /* java servlet */
    const val JAVA_SERVLET = "javax.servlet:javax.servlet-api:${DependenciesVersions.SERVLET_VERSION}"

    /* thymeleaf */
    const val THYMELEAF = "org.springframework.boot:spring-boot-starter-thymeleaf"

    /* spring app */
    const val VALIDATION = "org.springframework.boot:spring-boot-starter-validation"
    const val SPRING_SECURITY = "org.springframework.boot:spring-boot-starter-security"
    const val SPRING_WEB = "org.springframework.boot:spring-boot-starter-web"
    const val SPRING_WEB_VERSION = "org.springframework.boot:spring-boot-starter-web:${PluginVersions.SPRING_BOOT_VERSION}"
    const val SPRING_BOOT_STARTER = "org.springframework.boot:spring-boot-starter"
    const val TRANSACTION = "org.springframework:spring-tx:${DependenciesVersions.SPRING_TRANSACTION}"
    const val CONFIGURATION_PROCESSOR = "org.springframework.boot:spring-boot-configuration-processor"
    const val ACTUATOR = "org.springframework.boot:spring-boot-starter-actuator"

    /* gauth */
    const val GAUTH = "com.github.GSM-MSG:GAuth-SDK-Java:${DependenciesVersions.GAUTH_VERSION}"

    /* mail */
    const val EMAIL = "org.springframework.boot:spring-boot-starter-mail:${DependenciesVersions.EMAIL}"

    /* database */
    const val SPRING_DATA_JPA = "org.springframework.boot:spring-boot-starter-data-jpa"
    const val SPRING_JDBC = "org.springframework.boot:spring-boot-starter-jdbc"
    const val MYSQL_CONNECTOR = "com.mysql:mysql-connector-j"
    const val SPRING_REDIS = "org.springframework.boot:spring-boot-starter-data-redis"
    const val REDIS = "org.springframework.data:spring-data-redis:${DependenciesVersions.REDIS_VERSION}"
    const val MARIA_DB = "org.mariadb.jdbc:mariadb-java-client:${DependenciesVersions.MARIA_DB_VERSION}"
    const val MYSQL_DRIVER = "com.mysql:mysql-connector-j:${DependenciesVersions.MYSQL_DRIVER_VERSION}"

    /* aws */
    const val AWS_STARTER = "org.springframework.cloud:spring-cloud-starter-aws:${DependenciesVersions.AWS_STARTER}"

    /* queryDsl */
    const val QUERYDSL = "com.querydsl:querydsl-jpa:${DependenciesVersions.QUERYDSL}"
    const val QUERYDSL_PROCESSOR = "com.querydsl:querydsl-apt:${DependenciesVersions.QUERYDSL}:jpa"

    // kotlin
    const val KOTLIN_JACKSON = "com.fasterxml.jackson.module:jackson-module-kotlin"
    const val KOTLIN_STDLIB = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"


    /* logging */
    const val LOGGING = "io.github.microutils:kotlin-logging-jvm:${DependenciesVersions.LOG_VERSION}"

    /* test */
    const val SPRING_TEST = "org.springframework.boot:spring-boot-starter-test:${DependenciesVersions.SPRING_TEST}"
    const val MOCKK = "io.mockk:mockk:${DependenciesVersions.MOCKK}"
    const val KOTEST_RUNNER = "io.kotest:kotest-runner-junit5:${DependenciesVersions.KOTEST_JUNIT}"
    const val MOCKITOKOTLIN2 = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
    const val KOTEST_EXTENSION = "io.kotest.extensions:kotest-extensions-spring:${DependenciesVersions.KOTEST_EXTENSION}"
    const val KOTEST_ASSERTIONS = "io.kotest:kotest-assertions-core:${DependenciesVersions.KOTEST_ASSERTIONS}"

    /* jwt */
    const val JWT_API = "io.jsonwebtoken:jjwt-api:${DependenciesVersions.JWT_VERSION}"
    const val JWT_IMPL = "io.jsonwebtoken:jjwt-impl:${DependenciesVersions.JWT_VERSION}"
    const val JWT_JACKSON = "io.jsonwebtoken:jjwt-jackson:${DependenciesVersions.JWT_VERSION}"

    /* map struct */
    const val MAP_STRUCT = "org.mapstruct:mapstruct:${DependenciesVersions.MAP_STRUCT_VERSION}"
    const val MAP_STRUCT_PROCESSOR = "org.mapstruct:mapstruct-processor:${DependenciesVersions.MAP_STRUCT_VERSION}"

    /* fcm */
    const val FCM = "com.google.firebase:firebase-admin:${DependenciesVersions.FCM_VERSION}"
    const val APACHE_API_CLIENT = "org.apache.httpcomponents.client5:httpclient5:${DependenciesVersions.APACHE_API_CLIENT_VERSION}"
    const val APACHE_HTTP_2 = "org.apache.httpcomponents.core5:httpcore5-h2:${DependenciesVersions.APACHE_HTTP_2_VERSION}"
    const val APACHE_HTTP = "org.apache.httpcomponents.core5:httpcore5:${DependenciesVersions.APACHE_HTTP_VERSION}"

    /* prometheus */
    const val PROMETHEUS = "io.micrometer:micrometer-registry-prometheus"
}
