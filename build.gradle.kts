import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotestVersion = "4.6.3"

plugins {
    kotlin("jvm") version "1.5.30"
    application
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.ajalt:mordant:1.2.1")

    testImplementation(kotlin("test"))
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("kt.hello.MainKt")
}

// https://docs.gradle.org/current/dsl/org.gradle.api.tasks.JavaExec.html
// https://stackoverflow.com/questions/51810254/execute-javaexec-task-using-gradle-kotlin-dsl
task("execTestMain", JavaExec::class) {
    project.findProperty("mainClass")?.let {
        mainClass.set(it.toString())
    }

    classpath = sourceSets["test"].runtimeClasspath
}
