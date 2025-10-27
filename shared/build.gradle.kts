import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    id("com.rickclephas.kmp.nativecoroutines") version "1.0.0-ALPHA-47"
    kotlin("plugin.serialization") version "2.2.10"
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

        val commonTest by getting {
            dependencies {
                implementation("com.willowtreeapps.assertk:assertk:0.28.1")
            }
        }

        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
        }


        commonMain.dependencies {
            api("com.rickclephas.kmp:kmp-observableviewmodel-core:1.0.0-BETA-13")
            // put your Multiplatform dependencies here
            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.serialization.json)

            // Room
            implementation(libs.room.runtime)
            implementation(libs.sqlite.bundled)


        }

        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.koin.core)
            implementation(libs.koin.android)
            implementation(libs.koin.androidx.navigation)
            implementation(libs.koin.androidx.compose)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }


        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlin.testJunit)
            implementation(libs.assertk)
            implementation(kotlin("test-annotations-common"))
            implementation("com.willowtreeapps.assertk:assertk:0.28.1")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.2") // Konsistente Version
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.2")
            implementation("io.mockk:mockk:1.14.6")
            implementation("org.junit.jupiter:junit-jupiter:6.0.0")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

            implementation(kotlin("test"))
            implementation("io.ktor:ktor-client-mock:3.3.1") // MockEngine


        }
    }
    sourceSets.commonMain.dependencies {
        implementation(kotlin("test"))
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

room {
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    ksp(libs.room.compiler)
}
