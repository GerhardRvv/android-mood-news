plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.kotlin.kapt")
    alias(libs.plugins.hilt)
}

apply<MainGradlePlugin>()

android {
    namespace = "com.grv.feature_home"

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(project(":core:common"))
    api(project(":core:designsystem"))

    // Compose
    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)
    implementation(libs.androidx.compose.foundation)
    debugImplementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.constraint.layout)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.navigation.graph)

    // Java & Kotlin
    implementation(libs.androidx.activity)
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.fragment)
    implementation(libs.fragment.ktx)

    // Hilt Dependency Injection
    api(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.kotlin.stdlib.jdk7)

    // Androidx Security Crypto
    implementation(libs.androidx.security.crypto)

    // Retrofit
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.converter.gson)
    implementation(libs.squareup.okhttp)
    implementation(libs.squareup.logging.interceptor)
    implementation(libs.gson)

    // Test
    testImplementation(libs.truth)
    testImplementation(libs.mockk)
    androidTestImplementation(libs.mockk.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.core)

    testImplementation(libs.coroutines.test)
}