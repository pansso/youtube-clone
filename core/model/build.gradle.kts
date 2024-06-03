import com.youtubeclone.buildsrc.*

plugins {
    id("com.android.library")
    kotlin("android")
    id("kotlinx-serialization")
}

android {
    namespace = "com.youtubeclone.model"
}

dependencies {
    implementation(Dep.Kotlin.serializationJson)
    testImplementation("junit:junit:4.13.2")
}