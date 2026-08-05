import com.example.androidbase.presentation.BuildModules.CORE
import com.example.androidbase.presentation.BuildModules.DI
import com.example.androidbase.presentation.BuildModules.DOMAIN
import com.example.androidbase.presentation.ConfigData.COMPILE_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.MIN_SDK_VERSION
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.example.characters"
    compileSdk = COMPILE_SDK_VERSION

    defaultConfig {
        minSdk = MIN_SDK_VERSION
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    implementation(project(CORE))
    implementation(project(DOMAIN))
    implementation(project(DI))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.espresso)
    implementation(libs.kotlinx.coroutines.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.hilt.android)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.compose.navigation)
    implementation(libs.bundles.compose.testing)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.accompanist.swiperefresh)
}