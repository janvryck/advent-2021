package be.tabs_spaces.advent2021.util

import java.io.File
import java.io.FileNotFoundException

object InputReader {

    fun getInputAsString(day: Int): String {
        return fromResources(day).readText()
    }

    fun getInputAsList(day: Int): List<String> {
        return fromResources(day).readLines()
    }

    private fun fromResources(day: Int): File {
        return javaClass.classLoader.getResource("day-$day.txt")
            ?.let { File(it.toURI()) }
            ?: throw FileNotFoundException("No inputfile found for day $day")
    }
}