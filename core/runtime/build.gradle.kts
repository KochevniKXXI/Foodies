plugins {
    id("java-library")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.ksp)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    // Modules
    implementation(project(":core:model"))

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // DI Hilt for Kotlin library
    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)
}