@file:Suppress("ClassName", "MemberVisibilityCanBePrivate", "SpellCheckingInspection")

object Dependencies {

    object Androidx {
        object Core {
            private const val version = "1.0.1"
            val androidx_core = "androidx.core:core-ktx:${version}"
        }

        object ConstraintLayout {
            private const val version = "2.0.4"
            val androidx_constraintlayout = "androidx.constraintlayout:constraintlayout:${version}"
        }

        object Material {
            private const val version = "1.1.0-alpha04"
            val androidx_material = "com.google.android.material:material:${version}"
        }

        object Navigation {
            private const val version = "2.0.0"
            val androidx_navigation_fragment = "androidx.navigation:navigation-fragment-ktx:${version}"
            val androidx_navigation_ui = "androidx.navigation:navigation-ui-ktx:${version}"
        }

        object RecyclerView {
            private const val version = "1.0.0"
            val androidx_recyclerview = "androidx.recyclerview:recyclerview:${version}"
        }

        object Lifecycle {
            private const val version = "2.2.0"
            val extensions = "androidx.lifecycle:lifecycle-extensions:${version}"
            val compiler = "androidx.lifecycle:lifecycle-compiler:${version}"
            val testing = "androidx.arch.core:core-testing:${version}"
            val viewmodel_ktx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${version}"
        }

        object Compose {
            private const val version = "1.0.0-beta02"
            const val ui = "androidx.compose.ui:ui:$version"
            const val tooling = "androidx.compose.ui:ui-tooling:$version"
            const val foundation = "androidx.compose.foundation:foundation:$version"
            const val material = "androidx.compose.material:material:$version"
            const val uiTest = "androidx.compose.ui:ui-test-junit4:$version"
            const val compiler = "androidx.compose.compiler:compiler:$version"
            const val runtime = "androidx.compose.runtime:runtime:$version"
            const val material_icons = "androidx.compose.material:material-icons-extended:${version}"
        }

        object AppCompat {
            private val version = "2.2.0"

        }

        object Test {
            private val version = "1.1.1"
            val rules = "androidx.test:rules:${version}"
            val runner = "androidx.test:runner:${version}"

            object Espresso {
                private val version = "3.1.0"
                val core = "androidx.test.espresso:espresso-core:${version}"
            }
        }
    }

    object JUnit {
        private const val version = "4.13.1"
        val testlib_junit = "junit:junit:${version}"
    }

    object Kotlin {
        private const val version = "1.4.31"
        val plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${version}"
    }

    object Android {
        private const val version = "7.0.0-alpha09"
        val plugin = "com.android.tools.build:gradle:${version}"
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
        private const val version = "1.5.2"
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
            private const val version = "0.1.1"
            val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:${version}"
        }
    }

    object Koin {
        private const val version = "3.0.1-beta-1"
        val core = "io.insert-koin:koin-core:${version}"
        val android = "io.insert-koin:koin-android:${version}"
        val android_scope = "io.insert-koin:koin-android-scope:${version}"
        val android_viewmodel = "io.insert-koin:koin-androidx-viewmodel:${version}"
    }

    object MlKit {
        private const val version = "16.1.2"
        val text_recognition = "com.google.android.gms:play-services-mlkit-text-recognition:${version}"
    }


    object Camerax {
        private const val version = "1.0.0-rc1"
        val core = "androidx.camera:camera-core:${version}"
        val camera2 = "androidx.camera:camera-camera2:1.0.0-beta12"
        val lifecycle = "androidx.camera:camera-lifecycle:${version}"
        val view = "androidx.camera:camera-view:1.0.0-alpha19"
    }

    object Viewbinding {
        private const val version = "1.3.1"
        val delegate = "com.kirich1409.viewbindingpropertydelegate:vbpd-noreflection:${version}"
    }


    object SqlDelight {
        private const val version = "1.4.3"
        val gradle_plugin = "com.squareup.sqldelight:gradle-plugin:${version}"
        val runtime = "com.squareup.sqldelight:runtime:${version}"
        val coroutinesExtensions = "com.squareup.sqldelight:coroutines-extensions:${version}"
        val android_driver = "com.squareup.sqldelight:android-driver:${version}"
        val ios_driver = "com.squareup.sqldelight:native-driver:${version}"
    }
}

