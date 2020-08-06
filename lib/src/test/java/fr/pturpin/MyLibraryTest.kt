package fr.pturpin

import org.junit.Assert.assertEquals
import org.junit.Test

class MyLibraryTest {
    @Test
    fun zero_ShouldReturn0() {
        val myLibrary = MyLibrary()

        val zero = myLibrary.zero()

        assertEquals(zero, 0)
    }
}