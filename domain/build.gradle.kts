import com.example.androidbase.presentation.ConfigData.COMPILE_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.MIN_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.TARGET_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.TEST_INSTRUMENTATION_RUNNER
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.domain"
    compileSdk = COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = MIN_SDK_VERSION
        testInstrumentationRunner = TEST_INSTRUMENTATION_RUNNER
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.androidx.paging.common.android)
    testImplementation(libs.junit)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.espresso)
    implementation(libs.apollo.runtime)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
}