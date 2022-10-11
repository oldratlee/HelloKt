import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // variables - How to parametrize Kotlin version in the plugins block of a build.gradle.kts script?
    // https://stackoverflow.com/questions/60165254
    // How to get ext.* variables into plugins block in build.gradle.kts
    // https://stackoverflow.com/questions/46053522/47507441#47507441
    // Why can't I use val inside Plugins {}?
    // https://discuss.gradle.org/t/37098
    // False-positive "can't be called in this context by implicit receiver" with plugins in Gradle version catalogs as a TOML file
    // https://youtrack.jetbrains.com/issue/KTIJ-19369
    val kotlinVersion = "1.7.20"
    val dokkaVersion = "1.7.20"

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

// Mixing Kotlin and Java in a project
// https://subscription.packtpub.com/book/application_development/9781787126367/1/ch01lvl1sec15/mixing-kotlin-and-java-in-a-project
// Kotlin and Java sources
// https://www.kotlincn.net/docs/reference/using-gradle.html#kotlin-and-java-sources
listOf(sourceSets.main, sourceSets.test).forEach {
    it { java.srcDirs("src/${it.name}/java", "src/${it.name}/kotlin") }
}

dependencies {
    implementation("io.github.microutils:kotlin-logging:3.0.0")
    testRuntimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.19.0")

    implementation("com.github.ajalt:mordant:1.2.1")

    implementation("com.google.guava:guava:31.1-jre")
    implementation("org.apache.commons:commons-collections4:4.4")

    // https://github.com/hankcs/AhoCorasickDoubleArrayTrie
    // https://mvnrepository.com/artifact/com.hankcs/aho-corasick-double-array-trie
    //
    // PS: AhoCorasickDoubleArrayTrie also is contained in hanlp lib
    //     implementation("com.hankcs:hanlp:portable-1.8.2")
    //   https://github.com/hankcs/HanLP
    //   https://mvnrepository.com/artifact/com.hankcs/hanlp
    implementation("com.hankcs:aho-corasick-double-array-trie:1.2.3")

    // https://github.com/6tail/lunar-java
    implementation("cn.6tail:lunar:1.2.24")

    // test dependencies
    testImplementation(kotlin("test"))
    // https://kotest.io/docs/quickstart
    val kotestVersion = "5.5.1"
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("io.kotest:kotest-assertions-core:$kotestVersion")
    testImplementation("io.kotest:kotest-property:$kotestVersion")
}

java.sourceCompatibility = JavaVersion.VERSION_1_8
java.targetCompatibility = JavaVersion.VERSION_1_8

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

// https://kotest.io/docs/quickstart
tasks.test<Test> {
    useJUnitPlatform()
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
