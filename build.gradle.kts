import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
    kotlin("plugin.jpa") version "1.8.22"
}

group = "cn.zzwtsy"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    maven("https://maven.aliyun.com/repository/public/")
    maven("https://maven.aliyun.com/repository/spring/")
    mavenLocal()
    mavenCentral()
}

dependencies {
    // https://mvnrepository.com/artifact/org.jsoup/jsoup
    implementation("org.jsoup:jsoup:1.16.2")
    // https://mvnrepository.com/artifact/com.rometools/rome
    implementation("com.rometools:rome:2.1.0")
    // https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:okhttp-brotli:4.11.0")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.0.4")
    implementation("org.springframework.boot:spring-boot-starter-web:3.1.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect:1.8.10")
    runtimeOnly("com.mysql:mysql-connector-j:8.0.32")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
