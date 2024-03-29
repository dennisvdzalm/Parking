@file:Suppress("ClassName", "MemberVisibilityCanBePrivate", "SpellCheckingInspection")

object Dependencies {

    object Firebase {

        object AppDistribution {

            val plugin = "com.google.firebase:firebase-appdistribution-gradle:2.2.0"
        }
    }

    object Androidx {
        object Core {
            private const val version = "1.5.0"
            val androidx_core = "androidx.core:core-ktx:${version}"
        }

        object ConstraintLayout {
            private const val version = "2.0.4"
            val androidx_constraintlayout = "androidx.constraintlayout:constraintlayout:${version}"
        }

        object Material {
            private const val version = "1.4.0-rc01"
            val androidx_material = "com.google.android.material:material:${version}"
        }

        object Work {
            private const val work_version = "2.7.1"

            val runtime = "androidx.work:work-runtime-ktx:$work_version"
        }

        object AppCompat {
            private const val version = "1.3.0"
            const val appcompat = "androidx.appcompat:appcompat:$version"
        }

        object Activity {
            private const val version = "1.3.0-beta02"
            const val activity_compose = "androidx.activity:activity-compose:$version"
        }

        object Navigation {
            private const val version = "2.4.0-alpha10"
            const val navigation_compose = "androidx.navigation:navigation-compose:$version"
        }

        object RecyclerView {
            private const val version = "1.0.0"
            val androidx_recyclerview = "androidx.recyclerview:recyclerview:${version}"
        }

        object Lifecycle {
            private const val version = "2.4.1"
            val extensions = "androidx.lifecycle:lifecycle-extensions:${version}"
            val compiler = "androidx.lifecycle:lifecycle-compiler:${version}"
            val testing = "androidx.arch.core:core-testing:${version}"
            val viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${version}"

            object Compose {
                private const val version = "2.5.1"
                const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
            }
        }

        object Security {
            private const val version = "1.1.0-alpha03"
            const val crypto = "androidx.security:security-crypto:$version"
        }

        object Compose {
            const val bom = "androidx.compose:compose-bom:2022.12.00"
            const val ui = "androidx.compose.ui:ui"
            const val uiTooling = "androidx.compose.ui:ui-tooling"
            const val preview = "androidx.compose.ui:ui-tooling-preview"
            const val foundation = "androidx.compose.foundation:foundation"
            const val material = "androidx.compose.material:material"
            const val uiTest = "androidx.compose.ui:ui-test-junit4"
            const val material_icons = "androidx.compose.material:material-icons-extended"
        }

        object Test {
            private const val version = "1.1.1"
            val rules = "androidx.test:rules:${version}"
            val runner = "androidx.test:runner:${version}"

            object Espresso {
                private val version = "3.1.0"
                val core = "androidx.test.espresso:espresso-core:${version}"
            }
        }
    }

    object Accompanist {
        private const val version = "0.28.0"
        const val navigation_animation = "com.google.accompanist:accompanist-navigation-animation:$version"
    }

    object JUnit {
        private const val version = "4.13.2"
        val junit = "junit:junit:${version}"
    }

    object GradleDependencies {
        private const val version = "0.36.0"
        val plugin = "com.github.ben-manes:gradle-versions-plugin:${version}"
    }

    object Timber {
        private const val version = "4.7.1"
        val timber = "com.jakewharton.timber:timber:${version}"
    }

    object Ktor {
        private const val version = "2.2.2"
        val client_okhttp = "io.ktor:ktor-client-okhttp:${version}"
        val client_darwin = "io.ktor:ktor-client-darwin:${version}"
        val client_core = "io.ktor:ktor-client-core:${version}"
        val serialization = "io.ktor:ktor-serialization-kotlinx-json:${version}"
        val logging = "io.ktor:ktor-client-logging:${version}"
        val auth = "io.ktor:ktor-client-auth:${version}"
        val content_negotiation = "io.ktor:ktor-client-content-negotiation:${version}"
    }

    object Kotlinx {
        object Serialization {
            private const val version = "1.3.2"
            const val json = "org.jetbrains.kotlinx:kotlinx-serialization-json:${version}"
        }

        object Collections {
            const val immutable = "org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5"
        }

        object Coroutines {
            private const val version = "1.6.4"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${version}"
            val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${version}"
        }

        object DateTime {
            private const val version = "0.4.0"
            val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:${version}"
        }
    }

    object Koin {
        private const val composeVersion = "3.4.0"
        private const val coreVersion = "3.3.0"
        private const val androidVersion = "3.3.1"
        val core = "io.insert-koin:koin-core:$coreVersion"
        val android = "io.insert-koin:koin-android:$androidVersion"
        val workmanager = "io.insert-koin:koin-androidx-workmanager:$androidVersion"
        val compose = "io.insert-koin:koin-androidx-compose:$composeVersion"
    }

    object MlKit {
        private const val version = "16.2.0"
        val text_recognition = "com.google.android.gms:play-services-mlkit-text-recognition:${version}"
    }


    object Camerax {
        private const val version = "1.1.0-alpha05"
        val core = "androidx.camera:camera-core:${version}"
        val camera2 = "androidx.camera:camera-camera2:$version"
        val lifecycle = "androidx.camera:camera-lifecycle:${version}"
        val view = "androidx.camera:camera-view:1.0.0-alpha25"
    }

    object Viewbinding {
        private const val version = "1.4.5"
        val delegate = "com.github.kirich1409:viewbindingpropertydelegate-noreflection:${version}"
    }

    object MultiPlatformSettings {
        private const val version = "0.8.1"
        val settings = "com.russhwolf:multiplatform-settings:$version"
    }

}

