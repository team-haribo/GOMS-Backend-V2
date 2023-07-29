object Dependencies {
    /* kotlin */
    const val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect"
    const val KOTLIN_JDK = "org.jetbrains.kotlin:kotlin-stdlib-jdk8"

    /* java servlet */
    const val JAVA_SERVLET = "javax.servlet:javax.servlet-api:${DependenciesVersions.SERVLET_VERSION}"

    /* spring app */
    const val VALIDATION = "org.springframework.boot:spring-boot-starter-validation"
    const val SPRING_SECURITY = "org.springframework.boot:spring-boot-starter-security"
    const val SPRING_WEB = "org.springframework.boot:spring-boot-starter-web"
    const val TRANSACTION = "org.springframework:spring-tx:${DependenciesVersions.SPRING_TRANSACTION}"
    const val CONFIGURATION_PROCESSOR = "org.springframework.boot:spring-boot-configuration-processor"

    /* mail */
    const val EMAIL = "org.springframework.boot:spring-boot-starter-mail:${DependenciesVersions.EMAIL}"

    /* database */
    const val SPRING_DATA_JPA = "org.springframework.boot:spring-boot-starter-data-jpa"
    const val SPRING_JDBC = "org.springframework.boot:spring-boot-starter-jdbc"
    const val MYSQL_CONNECTOR = "com.mysql:mysql-connector-j"
    const val SPRING_REDIS = "org.springframework.boot:spring-boot-starter-data-redis"
    const val REDIS = "org.springframework.data:spring-data-redis:${DependenciesVersions.REDIS_VERSION}"

    /* queryDsl */
    const val QUERYDSL = "com.querydsl:querydsl-jpa:${DependenciesVersions.QUERYDSL}"
    const val QUERYDSL_PROCESSOR = "com.querydsl:querydsl-apt:${DependenciesVersions.QUERYDSL}:jpa"

    /* logging */
    const val LOGGING = "io.github.microutils:kotlin-logging-jvm:${DependenciesVersions.LOG_VERSION}"

    /* test */
    const val SPRING_TEST = "org.springframework.boot:spring-boot-starter-test:${DependenciesVersions.SPRING_TEST}"
    const val MOCKK = "io.mockk:mockk:${DependenciesVersions.MOCKK}"
    const val KOTEST_RUNNER = "io.kotest:kotest-runner-junit5:${DependenciesVersions.KOTEST_JUNIT}"
    const val KOTEST_EXTENSION = "io.kotest.extensions:kotest-extensions-spring:${DependenciesVersions.KOTEST_EXTENSION}"
    const val KOTEST_ASSERTIONS = "io.kotest:kotest-assertions-core:${DependenciesVersions.KOTEST_ASSERTIONS}"

    /* jwt */
    const val JWT_API = "io.jsonwebtoken:jjwt-api:${DependenciesVersions.JWT_VERSION}"
    const val JWT_IMPL = "io.jsonwebtoken:jjwt-impl:${DependenciesVersions.JWT_VERSION}"
    const val JWT_JACKSON = "io.jsonwebtoken:jjwt-jackson:${DependenciesVersions.JWT_VERSION}"

    /** code generator **/
    const val MAP_STRUCT = "org.mapstruct:mapstruct:${DependenciesVersions.MAP_STRUCT_VERSION}"
    const val MAP_STRUCT_PROCESSOR = "org.mapstruct:mapstruct-processor:${DependenciesVersions.MAP_STRUCT_VERSION}"
}