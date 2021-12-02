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