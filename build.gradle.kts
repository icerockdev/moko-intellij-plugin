plugins {
    id("org.jetbrains.intellij") version("0.6.5")
    id("org.jetbrains.kotlin.jvm") version("1.4.21")
}

repositories {
    mavenCentral()
}

group = "dev.icerock.moko"
version = "0.2.0-SNAPSHOT"

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    type = "IC"
    version = "201.8743.12"
    setPlugins(
        "android",
        "java"
    )
    isDownloadSources = true
    pluginName = "MOKO"

    alternativeIdePath = "/Users/alekseymikhailovwork/Library/Application Support/JetBrains/Toolbox/apps/AndroidStudio/ch-0/201.6953283/Android Studio.app"
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinCompile::class.java).all {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType(org.jetbrains.intellij.tasks.PublishTask::class.java).all {
    setToken(System.getProperty("JETBRAINS_TOKEN"))
}
