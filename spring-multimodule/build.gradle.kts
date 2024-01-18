import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    java
    application
    id("org.springframework.boot") version "2.5.2" apply false
    id("io.spring.dependency-management") version "1.0.11.RELEASE" apply false
    id("org.asciidoctor.jvm.convert") version "3.3.2"
    id("org.jlleitschuh.gradle.ktlint") version "10.2.0"
    kotlin("jvm") version "1.5.20"
    kotlin("kapt") version "1.5.20"
    kotlin("plugin.spring") version "1.5.20" apply false
    kotlin("plugin.jpa") version "1.5.20" apply false
    kotlin("plugin.allopen") version "1.5.20" apply false
}

allprojects {
    repositories {
        mavenCentral()
        google()
    }
}

subprojects {
    val queryDslVer = "4.4.0"
    val modelMapperVer = "2.3.8"

    apply {
        plugin("java")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jlleitschuh.gradle.ktlint")
        plugin("kotlin")
        plugin("kotlin-kapt")
        plugin("kotlin-spring")
        plugin("kotlin-jpa")
        plugin("kotlin-allopen")

        plugin("org.asciidoctor.jvm.convert")
    }

    group = "io.tourism.server"
    version = "0.0.1-SNAPSHOT"
    java.sourceCompatibility = JavaVersion.VERSION_11

    configurations {
        compileOnly {
            extendsFrom(configurations.annotationProcessor.get())
        }
    }

    dependencies {
        implementation("org.springframework.boot:spring-boot-starter-actuator")
        implementation("org.springframework.boot:spring-boot-starter-security")
        implementation("org.springframework.boot:spring-boot-starter-validation")
        implementation("org.springframework.boot:spring-boot-starter-web")
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        implementation("io.github.microutils:kotlin-logging:1.7.10")
        implementation("org.jetbrains.kotlin:kotlin-reflect")
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
        implementation("org.springframework.session:spring-session-core")
        implementation("com.querydsl:querydsl-jpa:$queryDslVer")
        implementation("org.modelmapper:modelmapper:$modelMapperVer")

        runtimeOnly("com.h2database:h2")
        runtimeOnly("mysql:mysql-connector-java")

        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.springframework.security:spring-security-test")
        testImplementation("com.ninja-squad:springmockk:3.0.1")

        kapt("com.querydsl:querydsl-apt:$queryDslVer:jpa")
        annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
        annotationProcessor("com.querydsl:querydsl-apt:$queryDslVer:jpa")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict", "-Xjvm-default=all")
            jvmTarget = "11"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        dependsOn(tasks.ktlintCheck)
    }

    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        verbose.set(true)
        disabledRules.addAll("import-ordering", "no-wildcard-imports", "filename", "indent")
    }
}

project(":core") {
    dependencies {
    }

    tasks.getByName<Jar>("jar") { enabled = true }
    tasks.getByName<BootJar>("bootJar") { enabled = false }
}

project(":common") {
    dependencies {
        implementation(project(":core"))
    }

    tasks.getByName<Jar>("jar") { enabled = true }
    tasks.getByName<BootJar>("bootJar") { enabled = false }
}

project(":api") {
    dependencies {
        implementation(project(":common"))
        implementation(project(":core"))

        // implementation("org.flywaydb:flyway-core")

        // jwt security
        implementation("io.jsonwebtoken:jjwt:0.9.1")

        // RestDocs
        testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    }

    val snippetsDir = file("build/generated-snippets")

    tasks.test {
        outputs.dir(snippetsDir)
    }

    tasks.asciidoctor {
        inputs.dir(snippetsDir)
        dependsOn(tasks.test)
    }

    tasks.withType<BootJar> {
        archiveFileName.set("tourism-api.jar")
    }

    tasks.register<Zip>("zip") {
        dependsOn("bootJar")
        archiveFileName.set("tourism-api.zip")

        from("build/libs/tourism-api.jar") { into("") }
        from("../.platform") { into(".platform") }
        from("../procfiles/api/Procfile") { into("") }
    }
}

project(":admin") {
    dependencies {
        implementation(project(":common"))
        implementation(project(":core"))
    }

    tasks.withType<BootJar> {
        archiveFileName.set("tourism-admin.jar")
    }

    tasks.register<Zip>("zip") {
        dependsOn("bootJar")
        archiveFileName.set("tourism-admin.zip")

        from("build/libs/tourism-admin.jar") { into("") }
        from("../.platform") { into(".platform") }
        from("../procfiles/admin/Procfile") { into("") }
    }
}
