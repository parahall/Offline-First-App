package com.android_academy.offline_first_app

import org.junit.Assert
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val link = "https://swapi.dev/api/people/13/"

        val split = link.split('/').dropLast(1).last()

        Assert.assertEquals("13",split)
    }
}