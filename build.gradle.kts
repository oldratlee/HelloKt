import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kotestVersion = "4.6.3"

plugins {
    // variables - How to parametrize Kotlin version in the plugins block of a build.gradle.kts script?
    // https://stackoverflow.com/questions/60165254
    // How to get ext.* variables into plugins block in build.gradle.kts
    // https://stackoverflow.com/questions/46053522/47507441#47507441
    // Why can't I use val inside Plugins {}?
    // https://discuss.gradle.org/t/37098
    // False-positive "can't be called in this context by implicit receiver" with plugins in Gradle version catalogs as a TOML file
    // https://youtrack.jetbrains.com/issue/KTIJ-19369
    val kotlinVersion = "1.5.31"
    val dokkaVersion = "1.5.30"

    // https://kotlinlang.org/docs/gradle.html
    kotlin("jvm") version kotlinVersion
    // https://kotlinlang.org/docs/kotlin-doc.html
    // https://github.com/Kotlin/dokka
    id("org.jetbrains.dokka") version dokkaVersion
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
