import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

buildscript {
    repositories {
        google()
        gradlePluginPortal()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.31")
        classpath("com.android.tools.build:gradle:7.0.0-alpha09")
        classpath(Dependencies.SqlDelight.gradle_plugin)
    }
}

group = "nl.dennisvanderzalm.parking"
version = "1.0-SNAPSHOT"

plugins {
    id("com.github.ben-manes.versions") version("0.38.0")
}

allprojects {
    repositories {
        mavenLocal()
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://kotlin.bintray.com/kotlinx/")
    }
}

/** Configuration for the [BenManesVersions] plugin */
tasks.named<DependencyUpdatesTask>("dependencyUpdates").configure {
    checkForGradleUpdate = true
    gradleReleaseChannel = "current"
    outputFormatter = "plain"
    outputDir = "${rootProject.buildDir}/dependencyUpdates"
    reportfileName = "report"
    revision = "release"

    fun unstableness(version: String): Int {
        val ranks = listOf("-m", "-rc", "-beta", "-alpha", "-snapshot")
        return ranks.indexOfFirst { version.toLowerCase().contains(it) }
    }

    rejectVersionIf {
        unstableness(candidate.version) > unstableness(currentVersion)
    }
}

subprojects {
    tasks.withType<JavaCompile>().configureEach {
        sourceCompatibility = ProjectConfig.javaVersion.toString()
        targetCompatibility = ProjectConfig.javaVersion.toString()
    }

    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = ProjectConfig.javaVersion.toString()
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
