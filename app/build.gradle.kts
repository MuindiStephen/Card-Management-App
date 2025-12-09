plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)

    id("dagger.hilt.android.plugin")
    id("kotlin-parcelize")
    id("kotlin-kapt")
}

android {
    namespace = "com.muindi.stephen.co_opbankapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.muindi.stephen.co_opbankapp"
        minSdk = 24
        targetSdk = 35
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
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Materia icons
    implementation(libs.androidx.material.icons.core)
    implementation("androidx.compose.material:material-icons-extended:1.7.8")

    // navigation compose
    implementation(libs.androidx.navigation.compose)

    //viewmodel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // system ui controller
    implementation(libs.accompanist.systemuicontroller)

    // ui util
    implementation(libs.androidx.ui.util.android)

    // datastore
    implementation(libs.androidx.datastore.preferences)

    //Networking
    implementation(libs.converter.gson)
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.moshi)

    // dagger hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // hilt navigation compose
    implementation(libs.androidx.hilt.navigation.compose)

    //room db
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    // Coil library for image loading
    implementation(libs.coil.compose)
}