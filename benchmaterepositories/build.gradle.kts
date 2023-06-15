plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("maven-publish")
}

group = "app.benchmate"
version = "0.0.4"

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        jvm()
        compilations.all {
            kotlinOptions {
                kotlinOptions { jvmTarget = JavaVersion.VERSION_1_8.toString() }
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "benchmaterepositories"
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                //put your multiplatform dependencies here
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

android {
    namespace = "app.benchmate.repositories"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
}

publishing.repositories.maven {

}