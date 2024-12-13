plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kapt)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.jetbrainsKotlinSerialization)
}

android {
    namespace = "com.example.dragonballwiki"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.dragonballwiki"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
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
    implementation(libs.androidx.material.extend)
    implementation(libs.androidx.constraintlayout.compose)


    //Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    kapt(libs.hilt.compiler)

    //Navigation
    implementation(libs.navigation.compose)
    implementation(libs.kotlinx.serialization.json)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.gson)
    implementation(libs.retrofit.converter)
    implementation(libs.logging.interceptor)

    //Coil
    implementation(libs.coil)

    //Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compile)

    testImplementation(libs.junit)
    testImplementation(libs.annotation)
    testImplementation(libs.junit.ktx)
    testImplementation(libs.junit.ext)
    testImplementation(libs.espresso)
    testImplementation(libs.espresso.contrib)
    testImplementation(libs.android.test.mockito)
    testImplementation(libs.android.test.mockito.inline)
    testImplementation(libs.test.coroutines)
    testImplementation(libs.mockito)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}