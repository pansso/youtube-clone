plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.youtubeclone.search"

    buildFeatures {
        dataBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:domain"))
    implementation(project(":core:common"))

    implementation(com.youtubeclone.buildsrc.Dep.Coil.coilCompose)
    com.youtubeclone.buildsrc.Dep.androidList.forEach(::implementation)
    com.youtubeclone.buildsrc.Dep.Compose.ComposeList.forEach(::implementation)
    com.youtubeclone.buildsrc.Dep.RactiveX.forEach(::implementation)
    implementation(com.youtubeclone.buildsrc.Dep.Hilt.hilt)
    kapt(com.youtubeclone.buildsrc.Dep.Hilt.compiler)

    testImplementation("junit:junit:4.13.2")
}