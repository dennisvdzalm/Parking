import com.android.build.gradle.internal.crash.afterEvaluate

buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Dependencies.Kotlin.plugin)
        classpath(Dependencies.Android.plugin)
        classpath(Dependencies.GradleDependencies.plugin)
        classpath(Dependencies.SqlDelight.gradle_plugin)
    }
}

group = "nl.dennisvanderzalm.parking"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenLocal()
        google()
        jcenter()
        gradlePluginPortal()
        mavenCentral()
        maven("https://kotlin.bintray.com/kotlinx/")
    }
}

subprojects {
    afterEvaluate { project ->
        if (project.hasProperty("android")) {
            apply {
                plugin("com.github.ben-manes.versions")
            }
        }
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}