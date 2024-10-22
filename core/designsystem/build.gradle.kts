plugins {
    id("com.android.library")
    id("kotlin-android")
    id("org.jetbrains.kotlin.kapt")
    alias(libs.plugins.hilt)
}

apply<MainGradlePlugin>()

android {
    namespace = "com.grv.core_designsystem"
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation(project(":core:common"))

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

    // Hilt Dependency Injection
    api(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.kotlin.stdlib.jdk7)

    // Hilt
    api(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    implementation(libs.kotlin.stdlib.jdk7)
}