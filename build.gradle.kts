buildscript {
    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:3.6.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.72")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
    }
}

plugins {
    id("org.sonarqube") version "3.0"
}

sonarqube {
    properties {
        property("sonar.projectKey", "TurpIF_android-coverage-test")
        property("sonar.organization", "turpif-github")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}
