package fr.pturpin

import org.junit.Assert.assertEquals
import org.junit.Test

class MyLibraryAndroidTest {
    @Test
    fun one_ShouldReturn1() {
        val myLibrary = MyLibrary()

        val one = myLibrary.one()

        assertEquals(one, 1)
    }
}