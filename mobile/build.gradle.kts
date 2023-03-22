plugins {
    //trick: for the same plugin versions in all sub-modules
    id(Dependencies.Plugins.androidLibrary).version(Dependencies.Plugins.Versions.androidPlugin).apply(false)
    id(Dependencies.Plugins.androidApplication).version(Dependencies.Plugins.Versions.androidPlugin).apply(false)
    kotlin("android").version(Dependencies.kotlinVersion).apply(false)
    kotlin("multiplatform").version(Dependencies.kotlinVersion).apply(false)
    kotlin(Dependencies.Plugins.serialization).version(Dependencies.kotlinVersion).apply(false)
    id(Dependencies.Plugins.sqlDelight).version(Dependencies.SQLDelight.sqlDelightVersion).apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
