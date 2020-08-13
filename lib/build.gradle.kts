plugins {
    id("com.android.library")
    kotlin("android")
    jacoco
}

android {
    compileSdkVersion(29)

    defaultConfig {
        minSdkVersion(29)
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.72")
    testImplementation("junit:junit:4.13")
}
