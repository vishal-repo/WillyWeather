package com.willyweather.assignment

import junit.framework.Assert.assertEquals
import org.junit.Test

class TestMockResponseFileReader{
    @Test
    fun `read simple file`(){
        val reader = MockResponseFileReader("test.json")
        assertEquals(reader.content, "success")
    }
}