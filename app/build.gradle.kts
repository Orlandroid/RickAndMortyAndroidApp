plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("androidx.navigation.safeargs.kotlin")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 32

    defaultConfig {
        applicationId = "com.rickandmortyorlando.orlando"
        minSdk = 21
        targetSdk = 32
        versionCode = 7
        versionName = "1.3"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    namespace = "com.rickandmortyorlando.orlando"
}

dependencies {


    val retrofit_version = "2.9.0"
    val navigation_version = "2.5.3"
    val lifecycle_version = "2.4.1"
    val dagger_hilt_version = "2.38.1"
    val paging_version = "3.1.1"

    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("com.squareup.retrofit2:retrofit:$retrofit_version")
    implementation("com.squareup.retrofit2:converter-gson:$retrofit_version")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("com.android.volley:volley:1.2.1")
    //GSON
    implementation("com.google.code.gson:gson:2.9.0")
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
    implementation("com.github.bumptech.glide:glide:4.14.2")
    annotationProcessor("com.github.bumptech.glide:compiler:4.14.2")
    implementation("com.squareup.picasso:picasso:2.71828")
    //Shimer
    implementation("com.facebook.shimmer:shimmer:0.5.0")
    implementation("com.faltenreich:skeletonlayout:4.0.0")
    implementation("androidx.paging:paging-runtime:$paging_version")
    implementation("com.airbnb.android:lottie:6.0.0")
}