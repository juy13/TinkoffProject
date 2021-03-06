import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.0-M3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.6.10"
	kotlin("plugin.spring") version "1.6.10"
}


group = "ru.tinkoff"
version = "0.0.1"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
	implementation("org.springdoc:springdoc-openapi-ui:1.6.7")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework:spring-test:5.3.18")
	implementation("com.h2database:h2:2.1.210")
	implementation("org.projectlombok:lombok:1.18.22")
	runtimeOnly("org.postgresql:postgresql:42.3.1")

	implementation("org.springframework.boot:spring-boot-starter-activemq")
	implementation("org.apache.activemq:activemq-broker")
	implementation("com.google.code.gson:gson")

	testImplementation("org.springframework.boot:spring-boot-starter-test")

	implementation("org.junit.jupiter:junit-jupiter:5.8.2")
	testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
	testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
	testImplementation("org.junit.jupiter:junit-jupiter-params:5.8.2")
	testImplementation("com.ninja-squad:springmockk:3.1.1")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.mockk:mockk:1.12.4")
	testImplementation("org.awaitility:awaitility-kotlin:4.1.0")

	implementation("com.auth0:java-jwt:3.19.2")
	implementation("org.springframework.security:spring-security-core:5.6.3")
	implementation("org.springframework.security:spring-security-web:5.6.3")
	implementation("org.springframework.security:spring-security-config:5.6.3")

	implementation("io.jsonwebtoken:jjwt:0.9.1")

}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

