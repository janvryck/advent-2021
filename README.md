# Advent of Code 2021
## Set-up
Initial set-up, including:

* `days.Day`,
* `util.InputReader`,
* `Runner` and
* Gradle setup

were adapted from [aoc-kotlin-starter](https://github.com/hughjdavey/aoc-kotlin-starter).

### Running
Run with Gradle, either

* running all `Days`:   
  `./gradlew run`
* or a specific `Day`:  
  `./gradlew run --args <day>`

#### Tests
Run tests through the IDE or run with Gradle, either
* running all tests:  
  `./gradlew test`
* or a specific test:  
  `./gradlew test --tests <testClassName>`

## Days
### Day 01 - Sonar Sweep
#### Part 1
Consider a sliding window, containing pairs of consecutive elements: count the number of pairs where the second number is higher than the first number.

| Index:       | 1          | 2          | 3          | 4          | 5          | ... |
|--------------|------------|------------|------------|------------|------------|-----|
| Original     | 199        | 200        | 208        | 210        | 200        | ... | 
| `zipWithNext` | (199, 200) | (200, 208) | (208, 210) | (210, 200) | (200, ...) | ... |

##### Useful docs/links:

* [`zipWithNext`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/zip-with-next.html)
* [`count(predicate: (Int) -> Boolean)`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/count.html)

#### Part 2
Expanding upon the method for Part 1:

* Consider a sliding window, containing triplets of consecutive elements:  
  Sum these triplets
* Using the resulting list:
  * consider a sliding window of consecutive pairs:  
    Count the number of pairs where the second number is higher than the first

##### Useful docs/links:
* [`windowed` retrieval from Collections](https://kotlinlang.org/docs/collection-parts.html#windowed)

### Day 02 - Dive!
#### Part 1
Pay attention to the values we are tracking: horizontal position and *depth*. Depth is the inverse of vertical position.

#### Part 2
Added a `ReferenceFrame` to the position, which modifies its movement behaviour.

##### Useful docs/links
* [Destructuring](https://kotlinlang.org/docs/destructuring-declarations.html)
* [Destructuring collections](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/component1.html)  
  This was useful for refactoring the factory method for `Instruction`

### Day 03 - Binary Diagnostic
### Part 1
* Create a `List` of `Char[]`
* Determine the occurrences per character for `Char[].indices`
* Find the max occurence per index

#### Useful docs/links:
* [Grouping collections](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-grouping/)
* [`eachCount`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/each-count.html)
* Convert a binary string to an `Int` using [`.toInt(radix)`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/to-int.html)

### Part 2
* Refactored to `Diagnostics` (`List`) and `Diagnostic` (`Char[]`)
* Split the logic into:
  * Determining all occurrences
  * Finding the highect occurrence
  * Filtering eacht `Diagnostic` by the most/least common character

_Pitfall_: Finding the max occurence should default to `1` if both `1` and `0` occur equally. Solved this by sorting the `Map` of occurrences by reversed natural order