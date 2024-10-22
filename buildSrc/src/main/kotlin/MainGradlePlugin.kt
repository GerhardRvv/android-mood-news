import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

class MainGradlePlugin: Plugin<Project> {

    override fun apply(project: Project) {
        applyPlugins(project)
        setProjectConfig(project)
    }

    private fun applyPlugins(project: Project) {
        project.apply {
            plugin("com.android.library")
            plugin("kotlin-android")
            plugin("kotlin-kapt")
            plugin("kotlin-parcelize")
        }
    }

    private fun setProjectConfig(project: Project) {

        project.android().apply {
            compileSdk = ProjectConfig.compileSdk

            defaultConfig {
                minSdk = ProjectConfig.minSdk
                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                consumerProguardFiles("consumer-rules.pro")
                multiDexEnabled = true
            }

            buildTypes {
            }

            buildFeatures {
                compose = true
                aidl = false
                buildConfig = false
            }

            composeOptions {
                kotlinCompilerExtensionVersion = "1.5.15"
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }

            flavorDimensions += "endpoint" + "system"

        }
    }

    private fun Project.android(): LibraryExtension {
        return extensions.getByType(LibraryExtension::class.java)
    }

}