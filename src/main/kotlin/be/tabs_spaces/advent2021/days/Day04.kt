package be.tabs_spaces.advent2021.days

class Day04: Day(4) {
    override fun partOne(): Any {
        val bingoSubsystem = BingoSubsystem(inputList)
        return bingoSubsystem.firstWinningBoard()
    }

    override fun partTwo(): Any {
        val bingoSubsystem = BingoSubsystem(inputList)
        return bingoSubsystem.lastWinningBoard()
    }

    class BingoSubsystem(rawInput: List<String>) {
        private val bingoNumbers: MutableList<Int> = rawInput.first().split(",").map { it.toInt() }.toMutableList()
        private val boards: MutableList<Board> = rawInput.subList(1, rawInput.size).chunked(6).map { Board(it) }.toMutableList()

        fun firstWinningBoard() = play()

        fun lastWinningBoard() = play(keepPlaying = true)

        private fun play(keepPlaying: Boolean = false): Int {
            var currentNumber = 0
            while (boards.none { it.isWinningBoard() }) {
                currentNumber = bingoNumbers.removeFirst()
                boards.forEach { it.crossOff(currentNumber) }
                if (keepPlaying && boards.size > 1) {
                    boards.removeIf { it.isWinningBoard() }
                }
            }
            return boards.first { it.isWinningBoard() }.totalSum() * currentNumber
        }
    }

    class Board(rawBoard: List<String>) {
        private var matrix: List<List<Int?>> = rawBoard
            .filter { it.isNotBlank() }
            .map { it.split(" ").mapNotNull { number -> number.toIntOrNull() } }

        fun crossOff(number: Int) {
            matrix = matrix.map { row -> row.map { if (it == number) null else it }}
        }

        fun isWinningBoard(): Boolean {
            return matrix.any { row -> row.mapNotNull{ it }.isEmpty() } ||
                    matrix.first().indices.any {
                        index -> matrix.mapNotNull { it[index] }.isEmpty()
                    }
        }

        fun totalSum() = matrix.sumOf { row -> row.mapNotNull { it }.sum() }
    }
}