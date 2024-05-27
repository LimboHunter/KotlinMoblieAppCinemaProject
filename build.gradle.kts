// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    val room_version = "2.6.1"

    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0-Beta4" apply false
    id("androidx.room") version "$room_version" apply false
    kotlin("kapt") version "1.9.23"
}