object Dependencies {

    const val kotlinVersion = "1.8.0"

    object Build {
        const val serialzationPlugin = "org.jetbrains.kotlin:kotlin-serialization:$kotlinVersion"
        const val sqlDelightPlugin =
            "com.squareup.sqldelight:gradle-plugin:${SQLDelight.sqlDelightVersion}"
    }

    object Plugins {
        const val serialization = "plugin.serialization"
        const val sqlDelight = "com.squareup.sqldelight" //this will change with newer version
        const val hilt = "com.google.dagger.hilt.android"
        const val kapt = "kapt"
        const val androidLibrary = "com.android.library"
        const val androidApplication = "com.android.application"

        object Versions {
            const val androidPlugin = "7.4.1"
        }
    }

    object Ktor {
        private const val ktorVersion = "2.2.3"
        const val core = "io.ktor:ktor-client-core:${ktorVersion}"
        const val clientJson = "io.ktor:ktor-client-json:${ktorVersion}"
        const val androidClient = "io.ktor:ktor-client-android:${ktorVersion}"
        const val logging = "io.ktor:ktor-client-logging:${ktorVersion}"
        const val iosClient = "io.ktor:ktor-client-ios:${ktorVersion}"
        const val javaClient = "io.ktor:ktor-client-java:$ktorVersion"
        const val contentNegotiation = "io.ktor:ktor-client-content-negotiation:$ktorVersion"
        const val serialization = "io.ktor:ktor-serialization-kotlinx-json:$ktorVersion"
        const val auth = "io.ktor:ktor-client-auth:$ktorVersion"
        const val qosLogback = "ch.qos.logback:logback-classic:1.4.5"
    }

    object Coroutines {
        const val coroutineVersion = "1.6.4"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$coroutineVersion"
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutineVersion"
    }

    object SQLDelight {
        const val sqlDelightVersion = "1.5.5"
        const val runtime = "com.squareup.sqldelight:runtime:${sqlDelightVersion}"
        const val androidDriver = "com.squareup.sqldelight:android-driver:${sqlDelightVersion}"
        const val nativeDriver = "com.squareup.sqldelight:native-driver:${sqlDelightVersion}"
        const val coroutineExtension =
            "com.squareup.sqldelight:coroutines-extensions:${sqlDelightVersion}"
    }

    object Kotlinx {
        private const val kotlinxDatetimeVersion = "0.1.1"
        const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:${kotlinxDatetimeVersion}"
    }

    object Compose {
        const val composeVersion = "1.3.0"
        const val composeDesktopVersion = "1.3.0"
        const val kotlinCompilerVersion = "1.5.10"

        const val runtime = "androidx.compose.runtime:runtime:${composeVersion}"
        const val runtimeLiveData = "androidx.compose.runtime:runtime-livedata:${composeVersion}"
        const val ui = "androidx.compose.ui:ui:${composeVersion}"
        const val material = "androidx.compose.material:material:${composeVersion}"
        const val materialIcon = "androidx.compose.material:material-icons-core:${composeVersion}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${composeVersion}"
        const val foundation = "androidx.compose.foundation:foundation:${composeVersion}"
        const val compiler = "androidx.compose.compiler:compiler:${composeVersion}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout-compose:.0.0-alpha03"
        const val activity = "androidx.activity:activity-compose:1.3.0-alpha08"
        const val navigation = "androidx.navigation:navigation-compose:1.0.0-alpha10"
    }

    object Koin {
        private const val koinVersion = "3.4.0"
        const val core = "io.insert-koin:koin-core:${koinVersion}"
        const val test = "io.insert-koin:koin-test:${koinVersion}"
        const val koinAndroid = "io.insert-koin:koin-android:${koinVersion}"
        const val koinAndroidCompose = "io.insert-koin:koin-androidx-compose:3.4.3"
        const val testJUnit4 = "io.insert-koin:koin-test-junit4:${koinVersion}"
    }

    object Settings {
        const val lib = "com.russhwolf:multiplatform-settings:1.0.0"
    }

    object Androidx {
        const val lifeCycleVersion = "2.6.0"
        const val securityCrypto = "androidx.security:security-crypto:1.0.0"
        const val viewmodel = "androidx.lifecycle:lifecycle-viewmodel-ktx:$lifeCycleVersion"
    }

    object Dagger {
        const val hiltVersion = "2.44"
        const val hiltAndroid = "com.google.dagger:hilt-android:${hiltVersion}"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${hiltVersion}"
    }
}