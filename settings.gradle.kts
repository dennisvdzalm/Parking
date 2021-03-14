include(":core")
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven(url = "https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}
rootProject.name = "Parking"


include(":parking-android")
include(":shared")
include(":shared:data")
include(":shared:core")
