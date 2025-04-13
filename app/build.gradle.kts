plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.devtools.ksp)
}

android {
    namespace = "com.example.fitness91"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.fitness91"
        minSdk = 29
        targetSdk = 35
        versionCode = 1
        versionName = "1.0.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isMinifyEnabled = false
            isShrinkResources = false
            isDebuggable = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    kotlin {
        jvmToolchain(17)
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.3"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    // core libs
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Kotlin Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.play.services)

    // compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.appcompat)
    androidTestImplementation(platform(libs.androidx.compose.bom))

    // tooling and preview
    implementation(libs.androidx.ui.tooling.preview)
    debugImplementation(libs.androidx.ui.tooling)

    // Compose - Material Design 3
    implementation(libs.androidx.material3)
    implementation(libs.compose.material3)
    implementation(libs.compose.material.icons.extended)
    implementation(libs.androidx.compose.material3.adaptive.navigation)

    // Compose - Integration with ViewModels
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Compose - Integration with Activities
    implementation(libs.androidx.activity.compose)

    // Compose - Testing
    androidTestImplementation(libs.compose.junit)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.androidx.uiautomator)
    androidTestImplementation(libs.truth)
    debugImplementation(libs.androidx.ui.test.manifest)
    androidTestImplementation(libs.androidx.ui.test.junit4)

    // Ui and graphics
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)

    // Navigation class
    implementation(libs.androidx.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    // loading images in compose
    implementation(libs.coil.compose)

    // Hilt
    implementation(libs.dagger.hilt.android)
    ksp(libs.dagger.hilt.compiler)

    // Hilt extension for compose
    implementation(libs.androidx.hilt.navigation.compose)

    // Supabase
    implementation(platform(libs.bom))
    implementation(libs.postgrest.kt)
    implementation(libs.gotrue.kt)
    implementation(libs.storage.kt)

    // Ktor client
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.utils)
    implementation(libs.ktor.utils.jvm)
    implementation(libs.ktor.client.logging.jvm)

    // Splash API
    implementation(libs.androidx.core.splashscreen)

    // coil lib to load images
    implementation(libs.coil3.coil.compose)
    implementation(libs.coil.network.okhttp)

    // data store
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.datastore.core)

    // plot graph
    implementation(libs.compose)
    implementation(libs.compose.m3)
    implementation(libs.core)
}