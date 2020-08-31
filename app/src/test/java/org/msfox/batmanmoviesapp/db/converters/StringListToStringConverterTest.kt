package org.msfox.batmanmoviesapp.db.converters

import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class StringListToStringConverterTest{

    @Test
    fun test(){
        val converter = StringListToStringConverter()

        val list = listOf("1", "2", "3", "4")
        val string = converter.listToString(list)
        val convertedList = converter.stringToList(string)

        Assert.assertEquals(4, convertedList.count())
        Assert.assertEquals("3", convertedList[2])
    }
}