plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hiltAndroid)
}

android {
    namespace = "ru.requestdesign.test.nomad.foodies"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.requestdesign.test.nomad.foodies"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt")
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Modules
    implementation(project(":core:data"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:network"))
    implementation(project(":core:runtime"))
    implementation(project(":feature:cart"))
    implementation(project(":feature:catalog"))
    implementation(project(":feature:product"))

    // Compose BOM
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.windowSizeClass)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // DI with hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
}