import com.example.androidbase.presentation.BuildModules.CORE
import com.example.androidbase.presentation.BuildModules.DI
import com.example.androidbase.presentation.BuildModules.DOMAIN
import com.example.androidbase.presentation.ConfigData.MIN_SDK_VERSION
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.android)
    id("dagger.hilt.android.plugin")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.locations"
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
    implementation(project(DOMAIN))
    implementation(project(DI))
    implementation(libs.android.material)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.test.espresso)
    androidTestImplementation(libs.test.junit)
    implementation(libs.bundles.compose)
    implementation(libs.bundles.compose.navigation)
    implementation(libs.bundles.compose.testing)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
}