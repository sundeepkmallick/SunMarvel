plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.android.dagger.hilt)
}

android {
    namespace = "com.sunmarvel.network.ktor"
}

dependencies {
    implementation(projects.storage)

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.serialization.jvm)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.cio)
    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.serialization.gson)
    implementation(libs.ktor.client.auth)
    implementation(libs.ktor.ktor.client.okhttp)
}