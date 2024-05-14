import com.youtubeclone.buildsrc.*

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
}

android {
    namespace = "com.youtubeclone.common"

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {

    Dep.Kotlin.CoroutineList.forEach(::implementation)
    Dep.RactiveX.forEach(::implementation)
    Dep.androidList.forEach(::implementation)

    testImplementation("junit:junit:4.13.2")
}