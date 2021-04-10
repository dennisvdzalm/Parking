import org.gradle.kotlin.dsl.kotlin
import org.gradle.plugin.use.PluginDependenciesSpec

/**
 * Gradle plugin to check if there are newer versions of dependencies available.
 *
 * See [documentation](https://github.com/ben-manes/gradle-versions-plugin)
 */
inline val PluginDependenciesSpec.BenManesVersions
    get() = id("com.github.ben-manes.versions").version("0.38.0")

/**
 * Gradle plugin to check if Jetifier can be disabled.
 * Read this [blog](https://adambennett.dev/2020/08/disabling-jetifier/) for more information
 *
 * See [documentation](https://github.com/plnice/can-i-drop-jetifier),
 */
inline val PluginDependenciesSpec.CanIDropJetifier
    get() = id("com.github.plnice.canidropjetifier").version("0.5")

/**
 * Gradle plugin that runs ktlint checks or does code auto formatting.
 *
 * See [documentation](https://github.com/jlleitschuh/ktlint-gradle)
 */
inline val PluginDependenciesSpec.Ktlint
    get() = id("org.jlleitschuh.gradle.ktlint").version("10.0.0")

/**
 * Gradle plugin for kotlinx serialization
 *
 * See [documentation](https://https://github.com/Kotlin/kotlinx.serialization)
 */
inline val PluginDependenciesSpec.Serialization
    get() = kotlin("plugin.serialization").version(ProjectConfig.kotlinVersion)
