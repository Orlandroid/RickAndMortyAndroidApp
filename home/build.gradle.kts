import com.example.androidbase.presentation.BuildModules.CORE
import com.example.androidbase.presentation.ConfigData.MIN_SDK_VERSION
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.home"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = MIN_SDK_VERSION
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(libs.android.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.test.espresso)
    androidTestImplementation(libs.test.junit)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.compose.navigation)
    implementation(libs.bundles.compose.testing)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.base.testing)
    testImplementation(libs.turbine)
}