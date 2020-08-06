package fr.pturpin

class MyLibrary {

    // This is covered by JVM tests in the same module
    fun zero(): Int {
        return 0
    }

    // This is covered by Instrumented tests in an other module
    fun one(): Int {
        return 1
    }
}