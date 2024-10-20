import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

internal val Project.libs: VersionCatalog
    get() =
        project.extensions.getByType<VersionCatalogsExtension>().named("libs")

plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    google()
    gradlePluginPortal()
}

dependencies {
    implementation(kotlin("script-runtime"))
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.25")
    implementation("com.android.tools.build:gradle:8.2.2")
}


val compileKotlin: KotlinCompile by tasks
val compileTestKotlin: KotlinCompile by tasks

compileKotlin.kotlinOptions {
    jvmTarget = "17"
}
compileTestKotlin.kotlinOptions {
    jvmTarget = "17"
}