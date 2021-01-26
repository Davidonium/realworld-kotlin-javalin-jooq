buildscript {
    repositories {
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath("org.jooq:jooq-codegen:3.14.1")
        classpath("org.postgresql:postgresql:42.2.11")
    }
}

plugins {
    kotlin("jvm") version "1.4.21"
    application
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
    id("com.github.johnrengelman.shadow") version "5.1.0"
}

group = "com.davidonium"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

application {
    mainClassName = "io.realworld.conduit.AppKt"
}

repositories {
    jcenter()
    mavenCentral()
}

sourceSets {
    main {
        java.srcDir("src/main/generated/java")
    }
}

val auth0_jwt_version = "3.10.0"
val bcrypt_version = "0.9.0"
val hikaricp_version = "3.4.1"
val jackson_version = "2.10.2"
val javalin_version = "3.7.0"
val jooq_version = "3.14.1"
val junit_version = "5.5.1"
val koin_version = "2.0.1"
val kotlin_version = "1.4.21"
val logback_version = "1.2.3"
val mockk_version = "1.10.3"
val postgres_driver_version = "42.2.6"
val rest_assured_version = "4.1.2"
val slugify_version = "2.4"
val spring_jdbc_version = "5.2.4.RELEASE"
val testcontainers_postgresql_version = "1.12.1"
val reactive_streams_version = "1.0.3"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlin_version")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlin_version")
    implementation("at.favre.lib:bcrypt:$bcrypt_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("com.auth0:java-jwt:$auth0_jwt_version")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jackson_version")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jackson_version")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jackson_version")
    implementation("com.zaxxer:HikariCP:$hikaricp_version")
    implementation("io.javalin:javalin:$javalin_version")
    implementation("org.jooq:jooq:$jooq_version")
    implementation("org.koin:koin-core:$koin_version")
    implementation("org.postgresql:postgresql:$postgres_driver_version")
    implementation("com.github.slugify:slugify:$slugify_version")
    implementation("org.springframework:spring-jdbc:$spring_jdbc_version")
    implementation("org.reactivestreams:reactive-streams:$reactive_streams_version")

    testImplementation("io.mockk:mockk:$mockk_version")
    testImplementation("io.rest-assured:rest-assured:$rest_assured_version")
    testImplementation("io.rest-assured:kotlin-extensions:$rest_assured_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:$kotlin_version")
    testImplementation("org.testcontainers:postgresql:$testcontainers_postgresql_version")
    testImplementation("org.junit.jupiter:junit-jupiter:$junit_version")
}

tasks {
    withType<Test> {
        useJUnitPlatform()
    }
    register("jooq") {
        group = "Jooq"
        description = "Generate jooq classes from the xml schema generated from the jooqxml task"

        inputs.file(file("${project.projectDir}/src/main/resources/db/jooq/information_schema.xml"))
        inputs.file(file("${project.projectDir}/src/main/resources/db/jooq/jooq.xml"))
        outputs.dir(file("${project.projectDir}/src/main/generated/java"))

        doLast {
            val config = file("${project.projectDir}/src/main/resources/db/jooq/jooq.xml")
                .readText()
                .replace("%project_dir%", project.projectDir.toString())

            org.jooq.codegen.GenerationTool.generate(config)
        }
    }
    register("jooqxml") {
        group = "Jooq"
        description = "Generate jooq xml schema from database"

        doLast {
            val dbUrl = System.getenv("DB_URL")
            val dbUser = System.getenv("DB_USER")
            val dbPwd = System.getenv("DB_PASSWORD")
            val config = file("${project.projectDir}/src/main/resources/db/jooq/jooq-meta.xml")
                .readText()
                .replace("%database_url%", dbUrl)
                .replace("%database_user%", dbUser)
                .replace("%database_password%", dbPwd)
                .replace("%project_dir%", project.projectDir.toString())

            org.jooq.codegen.GenerationTool.generate(config)
        }
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "11"
        }
        dependsOn(getByName("jooq"))
    }
    clean {
        delete("${project.projectDir}/src/main/generated")
    }
}

ktlint {
    filter {
        exclude("**/generated/**")
    }
}
