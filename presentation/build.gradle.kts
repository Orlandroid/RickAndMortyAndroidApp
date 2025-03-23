import com.example.androidbase.presentation.BuildModules.DATA
import com.example.androidbase.presentation.BuildModules.DOMAIN
import com.example.androidbase.presentation.ConfigData.COMPILE_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.MIN_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.TARGET_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.TEST_INSTRUMENTATION_RUNNER

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
        viewBinding = true
        dataBinding = true
        compose = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    namespace = "com.rickandmortyorlando.orlando"

}


dependencies {
    implementation(project(DATA))
    implementation(project(DOMAIN))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.android.material)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.espresso)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.interceptor)
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.volley)
    //GSON
    implementation(libs.gson)
    implementation(libs.kotlinx.serialization.json)
    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    // LiveData
    implementation(libs.androidx.lifecycle.livedata.ktx)
    //Dagger - Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    //Navigation component
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    //Image
    implementation(libs.glide)
    annotationProcessor(libs.compiler)
    //Shimer
    implementation(libs.shimmer)
    implementation(libs.skeletonlayout)
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.lottie)
    implementation(libs.apollo.runtime)
    implementation(libs.powerspinner)

    //Compose
    implementation(libs.androidx.ui)
    implementation(libs.androidx.material)
    implementation(libs.androidx.runtime)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.coil.compose)
    implementation(libs.androidx.constraintlayout.compose)
    implementation(libs.androidx.fragment.compose)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.landscapist.glide)
    implementation(libs.androidx.material3.android)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.paging.compose)
    implementation(libs.accompanist.swiperefresh)
    //Testing
    testImplementation(libs.mockwebserver)
    testImplementation(libs.truth)
    testImplementation(libs.kotlin.mockito.kotlin)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.turbine)
    implementation(libs.core.testing)
    testImplementation(libs.jetbrains.kotlinx.coroutines.test)
    testImplementation(libs.kotlin.mockito.kotlin)
    implementation(libs.kotlinx.coroutines.android)
    testImplementation(libs.jetbrains.kotlinx.coroutines.test)
    testImplementation(libs.core.testing)
    testImplementation(libs.junit)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.espresso)
    debugImplementation(libs.androidx.ui.tooling)


}


