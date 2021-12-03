package be.tabs_spaces.advent2021.days

import kotlin.math.pow

class Day03 : Day(3) {
    override fun partOne(): Any {
        val diagnostics = Diagnostics.fromInput(inputList)

        return diagnostics.gammaRate * diagnostics.epsilonRate
    }

    override fun partTwo(): Any {
        val diagnostics = Diagnostics.fromInput(inputList)

        return diagnostics.oxygenRating() * diagnostics.scrubberRating()
    }

    class Diagnostics private constructor(private val diagnostics: List<Diagnostic>) {
        private val size
            get() = diagnostics.size
        private val maxGammaRate by lazy { (2.0.pow(maxOccurrencesPerIndex().size) - 1).toInt() }

        val gammaRate by lazy { maxOccurrencesPerIndex().decimal }
        val epsilonRate by lazy { maxGammaRate - gammaRate }

        companion object {
            fun fromInput(rawDiagnostics: List<String>) = Diagnostics(rawDiagnostics
                .map { diagnostic -> diagnostic.indices.map { diagnostic[it] } }
                .map { Diagnostic(it) })
        }

        private fun occurrencesByIndex() = diagnostics.first().indices.map { idx -> diagnostics.map { it.get(idx) }.groupingBy { it }.eachCount() }

        private fun maxOccurrenceForIndex(index: Int) = occurrencesByIndex()[index].toSortedMap(naturalOrder<Char>().reversed()).maxByOrNull { it.value }?.key

        private fun maxOccurrencesPerIndex(): Diagnostic = Diagnostic(diagnostics.first().indices.mapNotNull { maxOccurrenceForIndex(it) })

        private fun filterByBitValue(index: Int, matching: Boolean = true) = Diagnostics(
            diagnostics.filter {
                (it.get(index) == maxOccurrenceForIndex(index)) == matching
            }
        )

        fun oxygenRating(): Int {
            var filtered = this
            (0 until diagnostics.first().size).forEach {
                if (filtered.size > 1) {
                    filtered = filtered.filterByBitValue(it)
                }
            }
            return filtered.diagnostics.first().decimal
        }

        fun scrubberRating(): Int {
            var filtered = this
            (0 until diagnostics.first().size).forEach {
                if (filtered.size > 1) {
                    filtered = filtered.filterByBitValue(it, false)
                }
            }
            return filtered.diagnostics.first().decimal
        }
    }

    data class Diagnostic(private val rawDiagnostic: List<Char>) {
        val indices
            get() = rawDiagnostic.indices
        val size
            get() = rawDiagnostic.size
        val decimal
            get() = rawDiagnostic.joinToString("").toInt(2)

        fun get(index: Int) = rawDiagnostic[index]
    }
}