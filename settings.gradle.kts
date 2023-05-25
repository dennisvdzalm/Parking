
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
rootProject.name = "Parking"


include(":core")
include(":parking-android")
include(":shared")
include(":shared:data")
include(":shared:core")
include(":shared:ui")
