plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(29)
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(project(":lib"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.72")
    androidTestImplementation("androidx.test:runner:1.2.0")
}