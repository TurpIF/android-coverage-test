package fr.pturpin

class MyLibrary {

    // This is covered by JVM tests in the same module
    fun zero(): Int {
        println("test")
        return 0
    }

    // This is covered by Instrumented tests in an other module
    fun one(): Int {
        println("test")
        return 1
    }

    // This is not covered by any tests
    @Suppress("unused")
    fun two(): Int {
        return 2
    }
}