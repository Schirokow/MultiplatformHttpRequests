import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    id("com.google.devtools.ksp") version "2.2.0-2.0.2"
    id("com.rickclephas.kmp.nativecoroutines") version "1.0.0-ALPHA-45"
    id("kotlin-kapt")
    kotlin("plugin.serialization") version "1.9.22"
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
        }
    }
    
    sourceSets {
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }


        commonMain.dependencies {
            api("com.rickclephas.kmp:kmp-observableviewmodel-core:1.0.0-BETA-12")
            // put your Multiplatform dependencies here
            implementation("io.insert-koin:koin-core:4.1.0")
            implementation(libs.ktor.client.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation("io.ktor:ktor-client-core:2.3.11")
            implementation("io.ktor:ktor-client-content-negotiation:3.2.3")
            implementation("io.ktor:ktor-serialization-kotlinx-json:3.2.3")
            implementation("io.ktor:ktor-serialization-kotlinx-xml:3.2.3")
            implementation("io.ktor:ktor-serialization-kotlinx-cbor:3.2.3")
            implementation("io.ktor:ktor-serialization-kotlinx-protobuf:3.2.3")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")

            //Room
            implementation("androidx.room:room-ktx:2.7.2")
//            kapt("androidx.room:room-compiler:2.7.2")

        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation("io.ktor:ktor-client-android:3.2.3")
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation("io.ktor:ktor-client-darwin:3.2.3")
        }


        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "org.example.multiplatformhttprequests.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }
}
