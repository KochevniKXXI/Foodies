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
    api(project(":core:model"))
    implementation(project(":core:network"))
    implementation(project(":core:runtime"))

    implementation(libs.kotlinx.coroutines.core)

    // DI Hilt for Kotlin library
    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)
}