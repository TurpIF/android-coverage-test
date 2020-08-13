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
    jacoco
}

sonarqube {
    properties {
        property("sonar.projectKey", "TurpIF_android-coverage-test")
        property("sonar.organization", "turpif-github")
        property("sonar.host.url", "https://sonarcloud.io")

        // There is no dependency in the Gradle graph here. One should first generate coverage
        // reports before invoking the sonarqube task. In this way, the CI can run JVM and
        // Android tests in parallel, and when both are finished, CI would invoke this task.
        val jacocoReports = subprojects.flatMap {
            val reportTree = it.fileTree(it.buildDir)
            reportTree.include("reports/jacoco/**/*.xml") // Reports from JVM tests
            reportTree.include("reports/coverage/**/*.xml") // Reports from Android tests
            reportTree.files
        }
        property("sonar.coverage.jacoco.xmlReportPaths", jacocoReports)
    }
}

/**
 * Generate XML and HTML Jacoco reports for all subprojects generating coverage data.
 *
 * This is only done for coverage data gathered via Java tests (unit tests). The Android Jacoco
 * tasks are completely independent from Gradle ones and generate themselves XML and HTML reports.
 *
 * Note that only reports of debug build type are generated.
 */
subprojects {
    // This wait for the setup of the plugin in subproject if it is applied.
    // So subprojects should opt-in to get their coverage.
    plugins.withType<JacocoPlugin>().configureEach {
        val jacocoTasks = tasks.matching {
            it.extensions.findByType<JacocoTaskExtension>() != null
        }

        tasks.register<JacocoReport>("codeCoverageReport") {
            val variant = "debug"

            group = "report"
            description = "Generate coverage report of JVM tests on $variant build"
            dependsOn(jacocoTasks)

            val androidExtensionClass = com.android.build.gradle.BaseExtension::class
            val android = this@subprojects.extensions.getByType(androidExtensionClass)
            val sourceDirs = listOf(
                    android.sourceSets["main"].java.srcDirs,
                    android.sourceSets[variant].java.srcDirs
            )

            val excludedGeneratedClasses = listOf(
                    "**/R.class",
                    "**/R$*.class",
                    "**/BuildConfig.class"
            )

            val classTree = fileTree(buildDir).apply {
                include("intermediates/javac/$variant/classes/**/*.class") // Java classes
                include("tmp/kotlin-classes/$variant/**/*.class") // Kotlin classes
                exclude(excludedGeneratedClasses)
            }

            val executionTree = fileTree(buildDir).apply {
                include("**/jacoco/*${variant.capitalize()}*.exec")
            }

            sourceDirectories.from(sourceDirs)
            classDirectories.from(classTree)
            executionData.from(executionTree)

            reports {
                xml.isEnabled = true
                html.isEnabled = true
            }
        }
    }
}
