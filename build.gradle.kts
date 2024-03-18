// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }


    dependencies {
        classpath(com.youtubeclone.buildsrc.Dep.androidGradlePlugin)
        classpath(com.youtubeclone.buildsrc.Dep.androidGradleApiPlugin)
        classpath(com.youtubeclone.buildsrc.Dep.Kotlin.gradlePlugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
//        classpath(com.example.buildsrc.Dep.Hilt.plugin)
    }
}
