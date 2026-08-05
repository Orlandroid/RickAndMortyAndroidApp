import com.example.androidbase.presentation.BuildModules.CHARACTERS
import com.example.androidbase.presentation.BuildModules.CORE
import com.example.androidbase.presentation.BuildModules.DATA
import com.example.androidbase.presentation.BuildModules.EPISODES
import com.example.androidbase.presentation.BuildModules.HOME
import com.example.androidbase.presentation.BuildModules.LOCATIONS
import com.example.androidbase.presentation.BuildModules.SETTINGS
import com.example.androidbase.presentation.ConfigData.COMPILE_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.MIN_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.TARGET_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.TEST_INSTRUMENTATION_RUNNER
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}


android {
    compileSdk = COMPILE_SDK_VERSION

    defaultConfig {
        applicationId = "com.rickandmortyorlando.app"
        minSdk = MIN_SDK_VERSION
        targetSdk = TARGET_SDK_VERSION
        versionCode = 9
        versionName = "1.4"

        testInstrumentationRunner = TEST_INSTRUMENTATION_RUNNER
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            isDebuggable = true
        }
    }
    buildFeatures {
        compose = true
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
    namespace = "com.rickandmortyorlando.orlando"

}


dependencies {
    implementation(project(DATA))
    implementation(project(CORE))
    implementation(project(CHARACTERS))
    implementation(project(LOCATIONS))
    implementation(project(EPISODES))
    implementation(project(SETTINGS))
    implementation(project(HOME))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.volley)
    //GSON
    implementation(libs.kotlinx.serialization.json)
    //Dagger - Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    //Compose
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.navigation.compose)
    //Testing
    testImplementation(libs.mockwebserver)
    testImplementation(libs.truth)
    testImplementation(libs.turbine)
    testImplementation(libs.core.testing)
    testImplementation(libs.junit)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.espresso)
    testImplementation(libs.jetbrains.kotlinx.coroutines.test)
    implementation(libs.kotlinx.coroutines.android)
    debugImplementation(libs.androidx.ui.tooling)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.test.manifest)

}


