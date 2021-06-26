plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.squareup.sqldelight")
    Serialization
}

group = "nl.dennisvanderzalm.parking"
version = "1.0-SNAPSHOT"

// workaround for https://youtrack.jetbrains.com/issue/KT-43944
android {
    configurations {
        create("androidTestApi")
        create("androidTestDebugApi")
        create("androidTestReleaseApi")
        create("testApi")
        create("testDebugApi")
        create("testReleaseApi")
    }
}
kotlin {
    android()
    ios {
        binaries {
            framework {
                baseName = "network"
            }
        }
    }


    repositories {
        google()
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":shared:core"))
                implementation(Dependencies.Ktor.client_core)
                implementation(Dependencies.Ktor.serialization)
                implementation(Dependencies.Ktor.logging)
                implementation(Dependencies.Ktor.auth)
                implementation(Dependencies.Kotlinx.Serialization.json)
                implementation(Dependencies.Kotlinx.Coroutines.core)
                implementation(Dependencies.Kotlinx.DateTime.datetime)
                implementation(Dependencies.Koin.core)
                implementation(Dependencies.SqlDelight.runtime)
                implementation(Dependencies.SqlDelight.coroutinesExtensions)
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Dependencies.Androidx.Material.androidx_material)
                implementation(Dependencies.Ktor.client_android)
                implementation(Dependencies.SqlDelight.android_driver)
                implementation(Dependencies.Androidx.Security.crypto)
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation(Dependencies.JUnit.junit)
            }
        }
        val iosMain by getting {
            dependencies {
                implementation(Dependencies.Ktor.client_ios)
                implementation(Dependencies.SqlDelight.ios_driver)
            }
        }
        val iosTest by getting
    }
}

android {
    compileSdkVersion(ProjectConfig.compileSdk)
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

sqldelight {
    database("Parking") {
        packageName = "nl.dennisvanderzalm.parking.db"
    }
}
