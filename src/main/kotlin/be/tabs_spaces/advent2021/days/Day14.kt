package be.tabs_spaces.advent2021.days

class Day14 : Day(14) {

    private val polymerizationEquipment = PolymerizationEquipment(inputList)

    override fun partOne() = polymerizationEquipment.diffPolymerizedOccurrences()

    override fun partTwo() = polymerizationEquipment.diffPolymerizedOccurrences(times = 40)

    class PolymerizationEquipment(rawInput: List<String>) {
        private val polymerTemplate: String = rawInput.first()
        private val pairInsertions: List<PairInsertionRule> = rawInput.drop(2)
            .map { it.split(" -> ") }
            .map { (pair, insertion) -> PairInsertionRule(pair[0] to pair[1], insertion[0]) }

        fun diffPolymerizedOccurrences(times: Int = 10) =
            countOccurrences(polymerize(times))
                .let { occurrences -> occurrences.maxOf { it.value } - occurrences.minOf { it.value } }

        private fun polymerize(times: Int) = (1..times).fold(polymerPairs(polymerTemplate)) { current, _ -> polymerize(current) }

        private fun countOccurrences(polymerized: Map<Pair<Char, Char>, Long>) =
            polymerized.entries
                .flatMap { (pair, occurrences) -> listOf(pair.first to occurrences, pair.second to occurrences) }
                .groupBy({ it.first }, { it.second })
                .mapValues {
                    when (it.key) {
                        in listOf(polymerTemplate.first(), polymerTemplate.last()) -> (it.value.sum() + 1) / 2
                        else -> it.value.sum() / 2
                    }
                }

        private fun polymerize(polymer: Map<Pair<Char, Char>, Long>) = polymer.entries
            .flatMap { (pair, occurrences) -> splitAndInsert(pair, occurrences) }
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.sum() }

        private fun splitAndInsert(pair: Pair<Char, Char>, occurrences: Long) = listOf(
            (pair.first to insertionFor(pair)) to occurrences,
            (insertionFor(pair) to pair.second) to occurrences,
        )

        private fun polymerPairs(polymer: String) = polymer.zipWithNext().groupingBy { it }.eachCount().mapValues { it.value.toLong() }

        private fun insertionFor(pair: Pair<Char, Char>) = pairInsertions.first { it.pair == pair }.insertion
    }

    data class PairInsertionRule(val pair: Pair<Char, Char>, val insertion: Char)

}