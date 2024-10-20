// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    val kotlinVersion by extra("1.9.25")
    val gradleVersion by extra("8.2.2")

    repositories {
        mavenCentral()
        google()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:$gradleVersion")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
    }
}

plugins {
}

allprojects {
    repositories {
        google()
        maven("https://jitpack.io")
        mavenCentral()
        gradlePluginPortal()
    }
}
