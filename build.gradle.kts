import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath("org.jooq:jooq-codegen:3.14.1")
        classpath("org.postgresql:postgresql:42.2.11")
    }
}

plugins {
    kotlin("jvm") version "1.5.0"
    application
    id("org.jlleitschuh.gradle.ktlint") version "9.4.1"
    id("com.github.johnrengelman.shadow") version "5.1.0"
}

group = "com.davidonium"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

application {
    mainClass.set("io.realworld.conduit.AppKt")
}

repositories {
    mavenCentral()
}

sourceSets {
    main {
        java.srcDir("src/main/generated/java")
    }
}

val auth0JwtVersion = "3.10.0"
val bcryptVersion = "0.9.0"
val hikaricpVersion = "3.4.1"
val jacksonVersion = "2.10.2"
val javalinVersion = "3.13.6"
val jooqVersion = "3.14.1"
val junitVersion = "5.5.1"
val koinVersion = "3.0.1"
val kotlinVersion = "1.5.0"
val logbackVersion = "1.2.3"
val mockkVersion = "1.10.3"
val postgresDriverVersion = "42.2.6"
val restAssuredVersion = "4.1.2"
val slugifyVersion = "2.4"
val springJdbcVersion = "5.2.4.RELEASE"
val testcontainersPostgresqlVersion = "1.15.1"
val reactiveStreamsVersion = "1.0.3"
val typesafeConfigVersion = "1.4.1"

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion")
    implementation("at.favre.lib:bcrypt:$bcryptVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("com.auth0:java-jwt:$auth0JwtVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    implementation("com.zaxxer:HikariCP:$hikaricpVersion")
    implementation("io.javalin:javalin:$javalinVersion")
    implementation("org.jooq:jooq:$jooqVersion")
    implementation("io.insert-koin:koin-core-jvm:$koinVersion")
    implementation("org.postgresql:postgresql:$postgresDriverVersion")
    implementation("com.github.slugify:slugify:$slugifyVersion")
    implementation("org.springframework:spring-jdbc:$springJdbcVersion")
    implementation("org.reactivestreams:reactive-streams:$reactiveStreamsVersion")
    implementation("com.typesafe:config:$typesafeConfigVersion")

    testImplementation("io.mockk:mockk:$mockkVersion")
    testImplementation("io.rest-assured:rest-assured:$restAssuredVersion")
    testImplementation("io.rest-assured:kotlin-extensions:$restAssuredVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:$kotlinVersion")
    testImplementation("org.testcontainers:postgresql:$testcontainersPostgresqlVersion")
    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
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
    withType<KotlinCompile> {
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
