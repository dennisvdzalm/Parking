plugins {
    kotlin("multiplatform")
    id("com.android.library")
    Serialization
}

group = "nl.dennisvanderzalm.parking"
version = "1.0-SNAPSHOT"

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

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
                implementation(Dependencies.Ktor.content_negotiation)
                implementation(Dependencies.Kotlinx.Serialization.json)
                implementation(Dependencies.Kotlinx.Coroutines.core)
                implementation(Dependencies.Kotlinx.DateTime.datetime)
                implementation(Dependencies.Koin.core)
                implementation(Dependencies.MultiPlatformSettings.settings)
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
                implementation(Dependencies.Ktor.client_okhttp)
                implementation(Dependencies.Androidx.Security.crypto)
            }
        }

        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(Dependencies.Ktor.client_darwin)
            }
        }
    }
}

android {
    compileSdk = ProjectConfig.compileSdk
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
    namespace = "nl.dennisvanderzalm.parking.shared.data"

    compileOptions {
        sourceCompatibility = ProjectConfig.javaVersion
        targetCompatibility = ProjectConfig.javaVersion
    }

    kotlin {
        jvmToolchain(ProjectConfig.jdkVersion)
    }
}

