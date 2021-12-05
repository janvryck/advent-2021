package be.tabs_spaces.advent2021.days

import java.lang.IllegalStateException
import kotlin.math.max
import kotlin.math.min

class Day05 : Day(5) {
    private val steamVents = inputList
        .map { it.split(" -> ") }
        .map { coordinates -> coordinates
            .map { it.split(",").map(String::toInt) }
            .map { it[0] to it[1] }
        }
        .map { SteamVent(it[0], it[1]) }

    override fun partOne(): Any {
        return steamVents
            .filterNot(SteamVent::isDiagonal)
            .flatMap(SteamVent::allPoints)
            .groupingBy { it }.eachCount()
            .filter { it.value > 1 }.count()
    }

    override fun partTwo(): Any {
        return steamVents
            .flatMap(SteamVent::allPoints)
            .groupingBy { it }.eachCount()
            .filter { it.value > 1 }.count()
    }

    data class SteamVent(
        val start: Pair<Int, Int>,
        val end: Pair<Int, Int>,
    ) {
       private val direction = when {
           start.first != end.first && start.second != end.second -> Direction.DIAGONAL
           start.first == end.first -> Direction.HORIZONTAL
           start.second == end.second -> Direction.VERTICAL
           else -> throw IllegalStateException()
       }

        fun allPoints() =  when(direction) {
            Direction.DIAGONAL -> (directionlessProgression(start.first, end.first)).zip(directionlessProgression(start.second, end.second))
            else -> (min(start.first, end.first)..max(start.first, end.first))
                .map { x -> (min(start.second, end.second)..max(start.second, end.second)).map { y -> x to y } }
                .flatten()
        }

        private fun directionlessProgression(from: Int, to: Int) = if (from < to) from..to else from downTo to

        fun isDiagonal() = direction == Direction.DIAGONAL
    }

    enum class Direction {
        HORIZONTAL,
        VERTICAL,
        DIAGONAL
    }
}