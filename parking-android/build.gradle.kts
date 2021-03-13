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
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Androidx.Compose.version
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(Dependencies.Androidx.Core.androidx_core)
    implementation(Dependencies.Androidx.Material.androidx_material)
    implementation(Dependencies.Androidx.ConstraintLayout.androidx_constraintlayout)
    implementation(Dependencies.Androidx.Lifecycle.viewmodel_ktx)
    implementation(Dependencies.Androidx.AppCompat.appcompat)

    //Compose dependencies
    implementation(Dependencies.Androidx.Compose.ui)
    implementation(Dependencies.Androidx.Compose.tooling)
    implementation(Dependencies.Androidx.Compose.compiler)
    implementation(Dependencies.Androidx.Compose.runtime)
    implementation(Dependencies.Androidx.Compose.foundation)
    implementation(Dependencies.Androidx.Compose.material)
    implementation(Dependencies.Androidx.Compose.material_icons)
    implementation(Dependencies.Androidx.Activity.activity_compose)
    implementation(Dependencies.Androidx.Lifecycle.Compose.viewModelCompose)

    implementation(Dependencies.MlKit.text_recognition)
    implementation(Dependencies.Camerax.core)
    implementation(Dependencies.Camerax.camera2)
    implementation(Dependencies.Camerax.lifecycle)
    implementation(Dependencies.Camerax.view)
    implementation(Dependencies.Camerax.core)
    implementation(Dependencies.Viewbinding.delegate)
    implementation(Dependencies.Timber.timber)
    implementation(Dependencies.Kotlinx.Coroutines.android)

    //koin
    implementation(Dependencies.Koin.android)
    implementation(Dependencies.Koin.koin_compose)
}