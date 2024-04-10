import com.youtubeclone.buildsrc.*

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.youtubeclone.youtubeplayer"

    buildFeatures {
        compose = true
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

dependencies {

    implementation(Dep.Coil.coil)
    implementation(Dep.Coil.coilCompose)
    Dep.androidList.forEach(::implementation)
    Dep.Compose.ComposeList.forEach(::implementation)

    testImplementation("junit:junit:4.13.2")
}