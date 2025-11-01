plugins {
    kotlin("jvm")
    application
}

group = "org.ac2dc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

val main = "org.ac2dc.MainKt"

application {
    // Define the main class for the application.
    mainClass = main
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = main
    }
}

tasks.named<JavaExec>("run") {
    standardInput = System.`in`
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}