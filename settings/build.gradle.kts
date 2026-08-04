import com.example.androidbase.presentation.BuildModules.CORE

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.android.built.in1.kotlin)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.settings"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
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
}