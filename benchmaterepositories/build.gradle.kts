plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("maven-publish")
    kotlin("plugin.serialization").version(libs.versions.serializationPlugin)
    id("app.cash.sqldelight").version(libs.versions.sqlDelightPlugin)
}

group = "app.benchmate"
version = "0.0.5"

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
//    targetHierarchy.default()

    jvm()

    androidTarget {
        task("testClasses")
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
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.serialization.kotlinx.json)
//                implementation(libs.runtime)
                implementation(libs.kotlinx.datetime)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.ktor.client.android)
//                implementation(libs.android.driver)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.jvm.driver)
            }
        }
//        val iosMain by getting {
//            dependencies {
//                implementation(libs.ktor.client.darwin)
//                implementation(libs.native.driver)
//            }
//        }
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

sqldelight {
    databases {
        create(name = "AppDatabase") {
            packageName.set("app.benchmate.repositories.db")
        }
    }
}