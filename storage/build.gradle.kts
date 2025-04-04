plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.android.dagger.hilt)
}

android {
    namespace = "com.sunmarvel.storage"
}

dependencies {

    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.androidx.security.crypto)
    // For Identity Credential APIs
    implementation(libs.androidx.security.identity.credential)
    // For App Authentication APIs
    implementation(libs.androidx.security.app.authenticator)
    // For App Authentication API testing
    androidTestImplementation(libs.androidx.security.app.authenticator)
}