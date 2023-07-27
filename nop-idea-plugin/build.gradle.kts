
plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.11.0"
}



group = "io.github.entropy-cloud"
version = "1.0-SNAPSHOT"


repositories {
    mavenLocal()
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2021.1")
    //version.set("2022.3")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf("java"))

}

dependencies {
    implementation( "io.github.entropy-cloud:nop-xlang-debugger:2.0.0-SNAPSHOT")
    implementation("io.github.entropy-cloud:nop-xlang:2.0.0-SNAPSHOT") {
        //exclude antlr4's dependency icu4j since it is not necessary and is too large.
        exclude(group = "com.ibm.icu")
    }
    testImplementation("junit:junit:4.13.2")
}

tasks {

    compileJava{
        options.encoding = "UTF-8"
    }

    compileTestJava{
        options.encoding = "UTF-8"
    }

    javadoc{
        options.encoding = "UTF-8"
    }

    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    patchPluginXml {
        sinceBuild.set("211")
        untilBuild.set("232.*")
    }

    signPlugin {
        certificateChain.set(System.getenv("CERTIFICATE_CHAIN"))
        privateKey.set(System.getenv("PRIVATE_KEY"))
        password.set(System.getenv("PRIVATE_KEY_PASSWORD"))
    }

    publishPlugin {
        token.set(System.getenv("PUBLISH_TOKEN"))
    }
}
