plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
}
group = "nl.dennisvanderzalm.parking"
version = "1.0-SNAPSHOT"

android {
    compileSdk = ProjectConfig.compileSdk
    defaultConfig {
        applicationId = "nl.dennisvanderzalm.parking"
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = ProjectConfig.javaVersion
        targetCompatibility = ProjectConfig.javaVersion
    }

    kotlin {
        jvmToolchain(ProjectConfig.jdkVersion)
    }

    namespace = "nl.dennisvanderzalm.parking"
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        freeCompilerArgs = freeCompilerArgs + listOf(
            "-P",
            "plugin:androidx.compose.compiler.plugins.kotlin:reportsDestination=${buildDir}/composeReports"
        )
    }
}

dependencies {
    implementation(project(":shared"))
    implementation(Dependencies.Androidx.Core.androidx_core)
    implementation(Dependencies.Androidx.Material.androidx_material)
    implementation(Dependencies.Androidx.ConstraintLayout.androidx_constraintlayout)
    implementation(Dependencies.Androidx.Lifecycle.viewmodel_ktx)
    implementation(Dependencies.Androidx.AppCompat.appcompat)
    implementation(Dependencies.Androidx.Work.runtime)

    //Compose dependencies
    implementation(platform(Dependencies.Androidx.Compose.bom))
    implementation(Dependencies.Androidx.Compose.ui)
    debugImplementation(Dependencies.Androidx.Compose.uiTooling)
    implementation(Dependencies.Androidx.Compose.preview)
    implementation(Dependencies.Androidx.Compose.foundation)
    implementation(Dependencies.Androidx.Compose.material)
    implementation(Dependencies.Androidx.Compose.material_icons)
    implementation(Dependencies.Androidx.Activity.activity_compose)
    implementation(Dependencies.Androidx.Navigation.navigation_compose)
    implementation(Dependencies.Androidx.Lifecycle.Compose.viewModelCompose)

    //accompanist dependencies
    implementation(Dependencies.Accompanist.navigation_animation)
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
    implementation(Dependencies.Koin.workmanager)
    implementation(Dependencies.Koin.androidxCompose)

    implementation(Dependencies.Kotlinx.DateTime.datetime)
    implementation(Dependencies.Kotlinx.Collections.immutable)

    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.1.5")
}
