plugins {
    id("com.android.application")
    kotlin("android")
    kotlin(Dependencies.Plugins.kapt)
    id(Dependencies.Plugins.hilt).version(Dependencies.Dagger.hiltVersion)
}

android {
    namespace = "com.ktnotes.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.ktnotes.android"
        minSdk = 23
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.3.1")
    implementation("androidx.compose.ui:ui-tooling:1.3.1")
    implementation("androidx.compose.ui:ui-tooling-preview:1.3.1")
    implementation("androidx.compose.foundation:foundation:1.3.1")
    implementation("androidx.compose.material:material:1.3.1")
    implementation("androidx.activity:activity-compose:1.6.1")
    implementation("androidx.navigation:navigation-compose:2.5.3")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
    implementation(Dependencies.Koin.core)
    implementation(Dependencies.Dagger.hiltAndroid)
    kapt(Dependencies.Dagger.hiltCompiler)
}