import com.example.androidbase.presentation.BuildModules.DATA
import com.example.androidbase.presentation.BuildModules.DOMAIN
import com.example.androidbase.presentation.ConfigData.COMPILE_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.MIN_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.TARGET_SDK_VERSION
import com.example.androidbase.presentation.ConfigData.TEST_INSTRUMENTATION_RUNNER
import com.example.androidbase.presentation.Dependencies.ANDROIDX_APPCOMPAT
import com.example.androidbase.presentation.Dependencies.ANDROIDX_CONSTRAINT_LAYOUT
import com.example.androidbase.presentation.Dependencies.ANDROIDX_CORE_KTX
import com.example.androidbase.presentation.Dependencies.ANDROID_MATERIAL
import com.example.androidbase.presentation.Dependencies.GOOGLE_GSON
import com.example.androidbase.presentation.Dependencies.JUNIT
import com.example.androidbase.presentation.Dependencies.RETROFIT
import com.example.androidbase.presentation.Dependencies.RETROFIT_CONVERTER_GSON
import com.example.androidbase.presentation.Dependencies.RETROFIT_INTERCEPTOR
import com.example.androidbase.presentation.Dependencies.TEST_EXPRESO
import com.example.androidbase.presentation.Dependencies.TEST_JUNIT

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
    //id("com.apollographql.apollo3") version "3.8.2"
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
    }
    buildFeatures {
        viewBinding = true
        dataBinding = true
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


/*
apollo {

    service("service") {

        packageName.set("com.example")

    }

}*/

dependencies {
    val navigation_version = "2.7.6"
    val lifecycle_version = "2.7.0"
    val dagger_hilt_version = "2.48"
    val paging_version = "3.2.1"
    implementation(project(DATA))
    implementation(project(DOMAIN))
    implementation(ANDROIDX_CORE_KTX)
    implementation(ANDROIDX_APPCOMPAT)
    implementation(ANDROID_MATERIAL)
    implementation(ANDROIDX_CONSTRAINT_LAYOUT)
    testImplementation(JUNIT)
    androidTestImplementation(TEST_JUNIT)
    androidTestImplementation(TEST_EXPRESO)
    implementation(RETROFIT)
    implementation(RETROFIT_CONVERTER_GSON)
    implementation(RETROFIT_INTERCEPTOR)
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.android.volley:volley:1.2.1")
    //GSON
    implementation(GOOGLE_GSON)
    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    //Dagger - Hilt
    implementation("com.google.dagger:hilt-android:$dagger_hilt_version")
    kapt("com.google.dagger:hilt-android-compiler:$dagger_hilt_version")
    //Navigation component
    implementation("androidx.navigation:navigation-fragment-ktx:$navigation_version")
    implementation("androidx.navigation:navigation-ui-ktx:$navigation_version")
    //Image
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")
    //Shimer
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation("com.faltenreich:skeletonlayout:5.0.0")
    implementation("androidx.paging:paging-runtime-ktx:$paging_version")
    implementation("com.airbnb.android:lottie:6.1.0")
    implementation("com.apollographql.apollo3:apollo-runtime:3.8.2")
}


