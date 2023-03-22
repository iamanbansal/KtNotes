plugins {
    kotlin("multiplatform")
    id(Dependencies.Plugins.androidLibrary)
    kotlin(Dependencies.Plugins.serialization)
    id(Dependencies.Plugins.sqlDelight)
}

kotlin {
    android()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Dependencies.Koin.core)

                with(Dependencies.Ktor) {
                    implementation(core)
                    implementation(logging)
                    implementation(contentNegotiation)
                    implementation(serialization)
                    implementation(auth)
                }

                implementation(Dependencies.Settings.lib)
                implementation(Dependencies.SQLDelight.coroutineExtension)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(Dependencies.Ktor.androidClient)
                implementation(Dependencies.Androidx.securityCrypto)
                implementation(Dependencies.Androidx.viewmodel)
                implementation(Dependencies.SQLDelight.androidDriver)
            }
        }
        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)

            dependencies {
                implementation(Dependencies.Ktor.iosClient)
                implementation(Dependencies.SQLDelight.nativeDriver)

            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.ktnotes"
    compileSdk = 33
    defaultConfig {
        minSdk = 23
        targetSdk = 33
    }
}

sqldelight {
    database("KtNotesDatabase") {
        packageName = "com.ktnotes.database"
    }
}