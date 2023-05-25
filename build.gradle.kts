import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask
import org.jetbrains.kotlin.gradle.tasks.UsesKotlinJavaToolchain
import org.jetbrains.kotlin.js.translate.context.Namer.kotlin

buildscript {
    repositories {
        google()
        gradlePluginPortal()
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${ProjectConfig.kotlinVersion}")
        classpath("com.android.tools.build:gradle:7.4.2")
    }
}

group = "nl.dennisvanderzalm.parking"
version = "1.0-SNAPSHOT"

plugins {
    BenManesVersions
    kotlin("android").version("1.8.10").apply(false)
    kotlin("multiplatform").version("1.8.10").apply(false)
    id("org.jetbrains.compose").version("1.4.0").apply(false)
}



allprojects {
    repositories {
        google()
        mavenLocal()
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


tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}
