import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
    alias(libs.plugins.hilt)
}

android {

    namespace = ProjectConfig.appId
    compileSdk = ProjectConfig.compileSdk

    defaultConfig {
        minSdk = ProjectConfig.minSdk
        targetSdk = ProjectConfig.targetSdk
        versionCode = 1
        versionName = ProjectConfig.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        multiDexEnabled = true

        val keystoreFile = project.rootProject.file("local.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        val apiKey = properties.getProperty("NEWS_API_TOKEN") ?: ""

        buildConfigField(
            type = "String",
            name = "NEWS_API_TOKEN",
            value = apiKey
        )
    }

    buildTypes {
        create("development") {
            initWith(getByName("development"))
            signingConfig = signingConfigs.getByName("debug")
            isDebuggable = true
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidxComposeCompiler.get()
    }

    buildFeatures {
        compose = true
        buildConfig = true
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain(17)
    }

    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    api(project(":feature:home"))
    api(project(":core:designsystem"))
    api(project(":core:common"))
    implementation(libs.androidx.navigation.compose)

    val composeBom = platform(libs.androidx.compose.bom)
    implementation(composeBom)
    androidTestImplementation(composeBom)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Androidx Security Crypto
    implementation(libs.androidx.security.crypto)

    // Retrofit
    implementation(libs.squareup.adapter.rxjava)
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.converter.jackson)
    implementation(libs.squareup.converter.gson)
    implementation(libs.squareup.okhttp)
    implementation(libs.squareup.logging.interceptor)
    implementation(libs.squareup.otto)
    implementation(libs.gson)

    // Hilt Dependency Injection
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
    androidTestImplementation(libs.hilt.android.testing)
    kaptAndroidTest(libs.hilt.android.compiler)

//    // Test
//    testImplementation(libs.truth)
//    testImplementation(libs.mockk)
//    androidTestImplementation(libs.mockk.android)
//
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.test.core)
//    androidTestImplementation(libs.androidx.test.runner)
//    androidTestImplementation(libs.androidx.test.rules)
//    androidTestImplementation(libs.androidx.test.core)
//
//    testImplementation(libs.coroutines.test)
}