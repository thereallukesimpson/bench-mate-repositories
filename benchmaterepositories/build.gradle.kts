plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("maven-publish")
    kotlin("plugin.serialization").version(libs.versions.serializationPlugin)
    id("app.cash.sqldelight").version(libs.versions.sqlDelightVersion)
    alias(libs.plugins.ksp)
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

//    jvm()
    
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
                implementation(libs.kotlinInject.runtime)
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
//        val jvmMain by getting {
//            dependencies {
//                implementation(libs.jvm.driver)
//            }
//        }
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

dependencies {
    implementation(libs.kotlinInject.runtime)

    // KSP will eventually have better multiplatform support and we'll be able to simply have
    // `ksp libs.kotlinInject.compiler` in the dependencies block of each source set
    // https://github.com/google/ksp/pull/1021
    add("kspAndroid", libs.kotlinInject.compiler)
//    kspAndroid("me.tatarka.inject:kotlin-inject-compiler-ksp:0.7.2")
//    add("kspIosX64", libs.kotlinInject.compiler)
//    add("kspIosArm64", libs.kotlinInject.compiler)
//    add("kspIosSimulatorArm64", libs.kotlinInject.compiler)
}