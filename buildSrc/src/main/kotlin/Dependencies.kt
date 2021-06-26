@file:Suppress("ClassName", "MemberVisibilityCanBePrivate", "SpellCheckingInspection")

object Dependencies {

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

        object AppCompat {
            private const val version = "1.3.0"
            const val appcompat = "androidx.appcompat:appcompat:$version"
        }

        object Activity {
            private const val version = "1.3.0-beta02"
            const val activity_compose = "androidx.activity:activity-compose:$version"
        }

        object Navigation {
            private const val version = "2.4.0-alpha03"
            const val navigation_compose = "androidx.navigation:navigation-compose:$version"
        }

        object RecyclerView {
            private const val version = "1.0.0"
            val androidx_recyclerview = "androidx.recyclerview:recyclerview:${version}"
        }

        object Lifecycle {
            private const val version = "2.3.1"
            val extensions = "androidx.lifecycle:lifecycle-extensions:${version}"
            val compiler = "androidx.lifecycle:lifecycle-compiler:${version}"
            val testing = "androidx.arch.core:core-testing:${version}"
            val viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${version}"

            object Compose {
                private const val version = "1.0.0-alpha07"
                const val viewModelCompose = "androidx.lifecycle:lifecycle-viewmodel-compose:$version"
            }
        }

        object Security {
            private const val version = "1.1.0-alpha03"
            const val crypto = "androidx.security:security-crypto:$version"
        }

        object Compose {
            const val version = "1.0.0-beta09"
            const val ui = "androidx.compose.ui:ui:$version"
            const val tooling = "androidx.compose.ui:ui-tooling:$version"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val material = "androidx.compose.material:material:$version"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:$version"
            const val compiler = "androidx.compose.compiler:compiler:$version"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val material_icons = "androidx.compose.material:material-icons-extended:${version}"
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
        private const val version = "1.6.0"
        val client_android = "io.ktor:ktor-client-android:${version}"
        val client_ios = "io.ktor:ktor-client-ios:${version}"
        val client_core = "io.ktor:ktor-client-core:${version}"
        val serialization = "io.ktor:ktor-client-serialization:${version}"
        val logging = "io.ktor:ktor-client-logging:${version}"
        val auth = "io.ktor:ktor-client-auth:${version}"
    }

    object Kotlinx {
        object Serialization {
            private const val version = "1.1.0"
            val json = "org.jetbrains.kotlinx:kotlinx-serialization-json:${version}"
        }

        object Coroutines {
            private const val version = "1.4.3"
            const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${version}"
            val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${version}"
        }

        object DateTime {
            private const val version = "0.2.1"
            val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:${version}"
        }
    }

    object Koin {
        private const val version = "3.1.1"
        val core = "io.insert-koin:koin-core:${version}"
        val android = "io.insert-koin:koin-android:${version}"
        val android_scope = "io.insert-koin:koin-android-scope:${version}"
        val android_viewmodel = "io.insert-koin:koin-androidx-viewmodel:${version}"
        val koin_compose = "io.insert-koin:koin-androidx-compose:${version}"
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


    object SqlDelight {
        private const val version = "1.5.0"
        val gradle_plugin = "com.squareup.sqldelight:gradle-plugin:${version}"
        val runtime = "com.squareup.sqldelight:runtime:${version}"
        val coroutinesExtensions = "com.squareup.sqldelight:coroutines-extensions:${version}"
        val android_driver = "com.squareup.sqldelight:android-driver:${version}"
        val ios_driver = "com.squareup.sqldelight:native-driver:${version}"
    }
}

