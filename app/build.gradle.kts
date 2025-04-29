plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)

}

android {
    namespace = "com.example.chatbot"
    compileSdk = 35

    buildFeatures {
        viewBinding = true
        dataBinding=true
    }

    defaultConfig {
        applicationId = "com.example.chatbot"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    //RecyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    implementation (libs.kotlinx.coroutines.android)
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    implementation ("com.squareup.picasso:picasso:2.71828")
    implementation ("com.google.code.gson:gson:2.10.1")

    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.datastore.core.android)
    implementation(libs.androidx.media3.common.ktx)
    implementation(libs.volley)
    implementation(libs.filament.android)
    implementation(libs.firebase.crashlytics.buildtools)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}