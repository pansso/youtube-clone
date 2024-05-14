import com.youtubeclone.buildsrc.*

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.youtubeclone.shorts"

    buildFeatures {
        dataBinding = true
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

dependencies {
    implementation(project(":core:designsystem"))
    implementation(project(":core:model"))
    implementation(project(":core:youtubeplayer"))
    implementation(project(":core:domain"))
    implementation(project(":core:common"))
    implementation(Dep.Coil.coil)

    Dep.androidList.forEach(::implementation)
    Dep.Compose.ComposeList.forEach(::implementation)
    implementation(Dep.Hilt.hilt)
    kapt(Dep.Hilt.compiler)

    testImplementation("junit:junit:4.13.2")
}