
plugins {
    id("com.android.application")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "uz.example.paymentapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "uz.example.paymentapp"
        minSdk = 26
        targetSdk = 36
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    // --- AndroidX ---
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.recyclerview)
    implementation(libs.cardview)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.lifecycle.viewmodel)
    implementation(libs.lifecycle.livedata)
    implementation(libs.androidx.annotation)

    // Example for iText
    implementation(libs.itextg)

    // --- DI (Hilt) ---
    implementation(libs.hilt.android)
    annotationProcessor(libs.hilt.android.compiler)


    // --- Room ---
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    implementation(libs.room.ktx)

    // --- Unit-test ---
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.androidx.core.testing)

    // --- UI / Instrumented-test ---
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.androidx.espresso.contrib)
    androidTestImplementation(libs.androidx.navigation.testing)
    androidTestImplementation(libs.mockito.android)

    // --- Utilities (KTX, аннотации) ---
    implementation(libs.androidx.core.ktx)
}
