// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.12.3")   // AGP
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.57.2")
    }
}
