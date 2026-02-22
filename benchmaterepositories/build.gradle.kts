plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    id("maven-publish")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.sqlDelight)
}

group = "app.benchmate"
version = "0.0.9"

//repositories {
//    google()
//    mavenCentral()
//}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
//    targetHierarchy.default()

    androidTarget {
        task("testClasses")
        compilations.all {
            kotlinOptions {
                kotlinOptions { jvmTarget = JavaVersion.VERSION_17.toString() }
            }
        }
        publishLibraryVariants("release", "debug")
    }

    jvm()
    
//    listOf(
//        iosX64(),
//        iosArm64(),
//        iosSimulatorArm64()
//    ).forEach {
//        it.binaries.framework {
//            baseName = "benchmaterepositories"
//        }
//    }

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
                implementation(libs.android.driver)
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
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
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