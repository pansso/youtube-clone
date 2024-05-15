import com.youtubeclone.buildsrc.*

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
}

android {
    namespace = "com.youtubeclone.main"

    buildFeatures {
        dataBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.0"
    }
}

dependencies {
    implementation(project(":feature:home"))
    implementation(project(":feature:library"))
    implementation(project(":feature:shorts"))
    implementation(project(":feature:subscriptions"))
    implementation(project(":feature:search"))
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    implementation(project(":core:common"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:youtubeplayer"))

    implementation(Dep.Coil.coilCompose)
    Dep.androidList.forEach(::implementation)
    Dep.Compose.ComposeList.forEach(::implementation)
    implementation(Dep.Hilt.hilt)
    kapt(Dep.Hilt.compiler)

    testImplementation("junit:junit:4.13.2")
}