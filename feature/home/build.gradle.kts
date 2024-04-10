import com.youtubeclone.buildsrc.*

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.youtubeclone.home"

    buildFeatures {
        dataBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

dependencies {

    implementation(Dep.Coil.coilCompose)
    Dep.androidList.forEach(::implementation)
    Dep.Compose.ComposeList.forEach(::implementation)
    Dep.RactiveX.forEach(::implementation)
    implementation(Dep.Hilt.hilt)
    kapt(Dep.Hilt.compiler)

    testImplementation("junit:junit:4.13.2")
}