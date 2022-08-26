plugins {
    id("org.springframework.boot") version "2.6.4"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm")
    kotlin("kapt")
    kotlin("plugin.spring") version "1.6.10"
}

group  = "ru.vega"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":model"))

    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    runtimeOnly("org.jetbrains.kotlin:kotlin-reflect:1.7.10")
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.mapstruct:mapstruct:1.5.2.Final")
    kapt("org.mapstruct:mapstruct-processor:1.5.2.Final")

    implementation("org.hibernate:hibernate-core:5.6.5.Final")
    implementation("com.h2database:h2:2.1.210")

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}