// Top-level build file where you can add configuration options common to all sub-projects/modules.
import com.example.buildsrc.*

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }


    dependencies {
        classpath(com.example.buildsrc.Dep.androidGradlePlugin)
        classpath(com.example.buildsrc.Dep.androidGradleApiPlugin)
        classpath(com.example.buildsrc.Dep.Kotlin.gradlePlugin)
        classpath(com.example.buildsrc.Dep.Hilt.plugin)
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
    }
}
