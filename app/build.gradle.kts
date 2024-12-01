import com.android.build.api.dsl.Packaging

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    kotlin("plugin.serialization") version "2.0.21"
    id("com.google.gms.google-services")
    id("io.realm.kotlin")
}

android {
    namespace = "com.example.se121p11new"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.se121p11new"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }

    packaging {
        resources {
            excludes += "META-INF/*"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.play.services.auth)
    testImplementation(libs.junit)
    testImplementation(libs.junit.jupiter.v560)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

//    Dagger - Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

//    Coil
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

//    Camera X
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.extensions)
    implementation(libs.androidx.camera.view)

//    ViewModel utilities for Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)

//    Compose Navigation
    implementation(libs.androidx.navigation.compose)

//    Material Icons Extended
    implementation(libs.androidx.material.icons.extended)

//    Kotlin Serialization
    implementation(libs.kotlinx.serialization.json)

//    Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)

//    Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.vertexai)
    implementation(libs.firebase.auth)

//    ML Kit Translation
    implementation(libs.translate)

//    Facebook SDK
    implementation(libs.facebook.login)

//    GCP Translation
    implementation(libs.google.cloud.translate) {
        exclude(group = "org.apache.httpcomponents")
        exclude(group = "org.json", module = "json")
    }
    annotationProcessor(libs.google.cloud.translate)

//    Realm Db
    implementation(libs.library.base)

//    Mockito
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    runtimeOnly(libs.mockito.android)


//    Kotlin Coroutines Test
    testImplementation(libs.kotlinx.coroutines.test)

//    Kotlinx DateTime
    implementation(libs.kotlinx.datetime)

}

kapt {
    correctErrorTypes = true
}