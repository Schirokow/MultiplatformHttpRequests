import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("kotlin-kapt")
    id("app.cash.sqldelight") version "2.1.0"
    kotlin("plugin.serialization") version "1.9.22"
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.example.app")
        }
    }
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    
    sourceSets {
        androidMain.dependencies {

            implementation(project(":shared"))

            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation("io.insert-koin:koin-core:4.1.0")
            implementation("io.insert-koin:koin-android:4.1.0")
            implementation("io.insert-koin:koin-androidx-navigation:4.1.0")
            implementation("io.insert-koin:koin-androidx-compose:4.1.0")
            implementation(libs.ktor.client.core)
            implementation(libs.kotlinx.coroutines.core)
            implementation("io.ktor:ktor-client-core:2.3.11")
            implementation("io.ktor:ktor-client-content-negotiation:3.2.3")
            implementation("io.ktor:ktor-serialization-kotlinx-json:3.2.3")
            implementation("io.ktor:ktor-serialization-kotlinx-xml:3.2.3")
            implementation("io.ktor:ktor-serialization-kotlinx-cbor:3.2.3")
            implementation("io.ktor:ktor-serialization-kotlinx-protobuf:3.2.3")
            implementation("io.ktor:ktor-client-android:3.2.3") // Ktor Android-Engine
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")

            //Room
            implementation("androidx.room:room-ktx:2.7.2")


            //Extended Icons
            implementation("androidx.compose.material:material-icons-extended:1.7.8")


        }
        commonMain.dependencies {

            implementation(project(":shared"))

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(projects.shared)
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
            implementation("androidx.room:room-common:2.7.2")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

android {
    namespace = "org.example.multiplatformhttprequests"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.example.multiplatformhttprequests"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }

    dependencies {
        add("kapt", "androidx.room:room-compiler:2.7.2")
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

}

dependencies {
    debugImplementation(compose.uiTooling)
}

