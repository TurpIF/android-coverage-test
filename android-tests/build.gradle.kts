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

    if (isGatheringCodeCoverage()) {
        buildTypes["debug"].isTestCoverageEnabled = true
        sourceSets["debug"].addDebugSourceSetsOf(project(":lib"))
    }
}

dependencies {
    implementation(project(":lib")) // Still needed when Gradle is not gathering coverage
    implementation("org.jetbrains.kotlin:kotlin-stdlib:1.3.72")
    androidTestImplementation("androidx.test:runner:1.2.0")
}

/**
 * When executing Android tests on debug, we want to gather the code coverage.
 * The sources of the tested library are in another module and the Android Jacoco does not seems
 * to handle cross-modules coverage.
 *
 * To handle this, the sources of the tested library are added in the classpath of the testing
 * module. However, this make the IDE unstable and it is not possible to easily develop in such
 * configuration. So classpath of testing module is extended only if a gradle property is defined.
 */
fun Project.isGatheringCodeCoverage(): Boolean {
    return properties["codeCoverage"] == "true"
}

fun com.android.build.gradle.api.AndroidSourceSet.addDebugSourceSetsOf(project: Project) {
    // Source sets should be defined during the configuration phase of Gradle.
    // So at this time, the other project is not necessary fully configured, and the android
    // extension might not be present yet.
    // Hence we have to hard-code the paths to the debug source sets of the other project.
    java.srcDir(project.file("src/main/java"))
    java.srcDir(project.file("src/debug/java"))
}
