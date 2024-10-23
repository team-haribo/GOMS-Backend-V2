plugins {
	kotlin("jvm") version PluginVersions.JVM_VERSION
}

repositories {
	mavenCentral()
}

subprojects {
	apply {
		plugin("org.jetbrains.kotlin.jvm")
		version = PluginVersions.JVM_VERSION
	}

	apply {
		plugin("org.jetbrains.kotlin.kapt")
		version = PluginVersions.KAPT_VERSION
	}

	dependencies {
		/* kotlin */
		implementation(Dependencies.KOTLIN_JDK)
		implementation(Dependencies.KOTLIN_REFLECT)

		/* logging */
		implementation(Dependencies.LOGGING)
		testImplementation(Dependencies.LOGGING)

		/* servlet */
		implementation(Dependencies.JAVA_SERVLET)

		/* test */
		testImplementation(Dependencies.SPRING_TEST) {
			exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
		}
		testImplementation(Dependencies.MOCKK)
		testImplementation(Dependencies.KOTEST_RUNNER)
		testImplementation(Dependencies.KOTEST_EXTENSION)
		testImplementation(Dependencies.KOTEST_ASSERTIONS)
	}
}

allprojects {
	group = "com.goms"
	version = "0.0.1-SNAPSHOT"
	java.sourceCompatibility = JavaVersion.VERSION_11

	tasks {
		compileKotlin {
			kotlinOptions {
				freeCompilerArgs = listOf("-Xjsr305=strict")
				jvmTarget = "11"
			}
		}
		compileJava {
			sourceCompatibility = JavaVersion.VERSION_11.majorVersion
		}
		test {
			useJUnitPlatform()
		}
	}

	repositories {
		mavenCentral()
		maven { url = uri("https://jitpack.io") }
	}
}
