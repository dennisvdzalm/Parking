plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}
group = "nl.dennisvanderzalm.parking"
version = "1.0-SNAPSHOT"

android {
    compileSdkVersion(ProjectConfig.compileSdk)
    defaultConfig {
        applicationId = "nl.dennisvanderzalm.parking"
        minSdkVersion(ProjectConfig.minSdk)
        targetSdkVersion(ProjectConfig.targetSdk)
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = ProjectConfig.javaVersion
        targetCompatibility = ProjectConfig.javaVersion
    }

    kotlin {
        kotlinOptions {
            jvmTarget = ProjectConfig.javaVersion.toString()
        }
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(Dependencies.Androidx.Core.androidx_core)
    implementation(Dependencies.Androidx.Material.androidx_material)
    implementation(Dependencies.Androidx.ConstraintLayout.androidx_constraintlayout)
    implementation(Dependencies.Androidx.Lifecycle.viewmodel_ktx)
    implementation(Dependencies.MlKit.text_recognition)
    implementation(Dependencies.Camerax.core)
    implementation(Dependencies.Camerax.camera2)
    implementation(Dependencies.Camerax.lifecycle)
    implementation(Dependencies.Camerax.view)
    implementation(Dependencies.Camerax.core)
    implementation(Dependencies.Viewbinding.delegate)
    implementation(Dependencies.Timber.timber)
    implementation(Dependencies.Kotlinx.Coroutines.android)
    implementation(Dependencies.Koin.android)
}