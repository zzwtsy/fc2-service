import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
    id("com.google.devtools.ksp") version "1.9.21-1.0.16"
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.spring") version "1.9.21"
}

group = "cn.zzwtsy"
version = "0.0.1-SNAPSHOT"
val jimmerVersion = "0.8.69"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

repositories {
    mavenLocal()
    maven("https://maven.aliyun.com/repository/spring/")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    mavenCentral()
}

dependencies {
    implementation("org.jsoup:jsoup:1.16.2")
    implementation("com.rometools:rome:2.1.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:okhttp-brotli:4.12.0")
    implementation("io.github.oshai:kotlin-logging-jvm:5.0.0")
    implementation("net.coobird:thumbnailator:0.4.20")
    implementation("org.babyfish.jimmer:jimmer-spring-boot-starter:${jimmerVersion}")
    implementation("org.springframework.boot:spring-boot-starter-web:3.1.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:1.7.3")
    // 将 libs 文件夹下的 jar 添加到依赖
    implementation(fileTree("libs") { include("*.jar") })

    runtimeOnly("com.mysql:mysql-connector-j:8.2.0")

    ksp("org.babyfish.jimmer:jimmer-ksp:${jimmerVersion}")
    testImplementation("org.springframework.boot:spring-boot-starter-test:3.1.0")
}

// 将 jimmer 生成的代码添加到编译路径中。
// 没有这个配置，gradle命令仍然可以正常执行，
// 但是, Intellij无法找到生成的源码。
kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "21"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

