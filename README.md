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

### Day 1 - Sonar Sweep

#### Part 1

Consider a sliding window, containing pairs of consecutive elements: count the number of pairs where the second number is higher than the first number.

| Index:        | 1          | 2          | 3          | 4          | 5          | ... |
|---------------|------------|------------|------------|------------|------------|-----|
| Original      | 199        | 200        | 208        | 210        | 200        | ... | 
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

#### Part 2 - Update

Refactored _Part 2_ after
reading [Anton Arhipov's](https://github.com/antonarhipov) [blog on JetBrains](https://blog.jetbrains.com/kotlin/2021/12/advent-of-code-2021-in-kotlin-day-1/).

There is no need for double sliding windows, since the triplet-sized windows only have 2 values (the first one for window A, last one for window B) not in
common. Using a window with size 4, allows us to evaluate the triplet-sized windows in one passing.

| Index:   | 1   | 2   | 3   | 4   | 5   | ... |
|----------|-----|-----|-----|-----|-----|-----|
| Original | 199 | 200 | 208 | 210 | 200 | ... | 
| Window A | 199 | 200 | 208 |     |     | ... |
| Window B |     | 200 | 208 | 210 |     | ... |

```
  window A = 199 + 200 + 208 = 607 
  window B = 200 + 208 + 210 = 618
  
  window B - window A = 210 - 199 = 11 
```

### Day 2 - Dive!

#### Part 1

Pay attention to the values we are tracking: horizontal position and *depth*. Depth is the inverse of vertical position.

#### Part 2

Added a `ReferenceFrame` to the position, which modifies its movement behaviour.

##### Useful docs/links

* [Destructuring](https://kotlinlang.org/docs/destructuring-declarations.html)
* [Destructuring collections](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/component1.html)  
  This was useful for refactoring the factory method for `Instruction`

### Day 3 - Binary Diagnostic

#### Part 1

* Create a `List` of `Char[]`
* Determine the occurrences per character for `Char[].indices`
* Find the max occurence per index

##### Useful docs/links:

* [Grouping collections](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-grouping/)
* [`eachCount`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/each-count.html)
* Convert a binary string to an `Int` using [`.toInt(radix)`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.text/to-int.html)

#### Part 2

* Refactored to `Diagnostics` (`List`) and `Diagnostic` (`Char[]`)
* Split the logic into:
    * Determining all occurrences
    * Finding the highect occurrence
    * Filtering eacht `Diagnostic` by the most/least common character

_Pitfall_: Finding the max occurrence should default to `1` if both `1` and `0` occur equally. Solved this by sorting the `Map` of occurrences by reversed
natural order

##### `by lazy { }`

Added lazy initialization of the values from _Part 1_ in order to only calculate them when needed for _Part 1_.

##### Useful docs/links:

* [`Lazy properties`](https://kotlinlang.org/docs/delegated-properties.html#lazy-properties)

### Day 4 - Giant Squid

#### Part 1

* Map the boards to a 2D List
* Cross of numbers:
    * Replace matching numbers by `null`
* Win condition, either:
    * Find a `null`-row
    * Find a `null`-column by iterating over all indices in the rows
* Play while no boards are in the winning condition
    * Pop the numbers from a `MutableList` using `removeFirst`

#### Useful docs/links:

* [`removeFirst`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/remove-first.html)
* [`MutableList`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/-mutable-list/)

#### Part 2

* Store the boards in a `MutableList`
* Remove each winning board while playing, until only one is left

### Day 5 - Hydrothermal Venture

#### Part 1

* Transform the raw coordinate-pairs into a `SteamVent` containing a start and end
* Filter the result, removing `DIAGONAL` directions
* Calculate all points for the given pair
    * `min()` and `max()` were useful for creating valid progressions
* Group the result by their identity, and count their occurrences

#### Useful docs/links:

* [Progressions](https://kotlinlang.org/docs/ranges.html#progression)
* [`min`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.math/min.html) and [`max`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.math/max.html)

#### Part 2

Following the same logic as _Part 1_, but don't remove the `DIAGONAL` entries. Calculating all points for these `SteamVents` was a bit more challenging, but
using `zip()` made it trivial in the end.

To calculate all points on a diagonal:

* Create a progression from `first.x` to `second.x`  
  Using `downTo` should `first.x > second.x`
* `Zip` this with a progression from `first.y` to `second.y`  
  `zip` creates pairs of corresponding entries in two lists.

#### Useful docs/links:

* [`downTo`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.ranges/down-to.html)
* [`zip`](https://kotlinlang.org/docs/collection-transformations.html#zip)

### Day 6 - Lanternfish

#### Part 1

* Create a `Map` of _number of fish_ by _days until spawning_
* Count the number of fish which will spawn: `fishByAge[0]`
* For each entry:
    * decrease the _days until spawning_
    * reset the fish which have just spawned offspring
    * add an entry for newly spawned fish

| Days until spawn | Initial | Day 1 | Day 2                        | Day 3 | Day 4         |
|------------------|---------|-------|------------------------------|-------|---------------|
| 0                | 0       | 1     | 1                            | 2     | 1             |
| 1                | 1       | 1     | 2                            | 1     | 0             |
| 2                | 1       | 2     | 1                            | 0     | 0             |
| 3                | 2       | 1     | 0                            | 0     | 0             |
| 4                | 4       | 0     | 0                            | 0     | 1             |
| 5                | 0       | 0     | 0                            | 1     | 1             |
| 6                | 0       | 0     | **1** (reset after spawning) | **1** | 1 + **2** = 3 |
| 7                | 0       | 0     | 0                            | 1     | 1             |  
| 8                | 0       | 0     | **1** (newly spawned)        | **1** | 2             |

#### Part 2

Change from `Map<Int, Int>` to `Map<Int, Long>` since the numbers increased too much.

### Day 7 - The Treachery of Whales

#### Part 1

* Group the crabs by position
* For each possible target position between the lowest and highest coordinate
    * Calculate the distance between the target and each crab position
    * Multiply the distance by the number of crabs on that position
    * Sum all these numbers to find the total required fuel consumption
* Find the lowest resulting fuel consumption

#### Useful docs/links:

* [`minOf`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/min-of.html)
* [`sumOf`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin.collections/sum-of.html)

#### Part 2

**First approach:**  
After calculating the distance between a target and crab position:

* Create a `Progression` from `1 .. distance`
* Sum all values in the `Progression`

While this resulted in a working solution, it took over _2.5s_ for calculating the result with the puzzle input.

**Second approach:**
Use a _Gauss Summation_ for calculating the non-linear fuel consumption. This states that the sum of all natural number from `1 to N` is given by the following
formula:  
`S = N * (N+1)/2`

This approach led to a run time of only _24ms_, 2 orders of magnitude quicker.

### Day 8 - Seven Segment Search

#### Part 1

* Split the input on ` | `, retain the second part and split on ` `
* Count the lengths of all resulting strings
* Count the number of string of length `2`, `4`, `3` or `7`

#### Part 2

##### Deducing numbers from the signal patterns

I've made up some (probably sub-optimal) rules for deducing all numbers based on the scrambled signal patterns:

* `ONE` has a signal pattern with unique length `2`
* `FOUR` has a signal pattern with unique length `4`
* `SEVEN` has a signal pattern with unique length `3`
* `EIGHT` has a signal pattern with unique length `7`
* `TWO` is the only pattern which lacks the `f`-segment  
  Built a frequency of the segments per scrambled pattern,  
  Filtered the scrambled patterns for the one **not** containing the most frequent segment
* `THREE` is the only remaining pattern of length `5`, containing all segments of `SEVEN`
* `FIVE` is the only remaining pattern of length `5`
* `ZERO` is the only remaining pattern which does **not** contain all segments of `FIVE`
* `NINE` is the only remaining pattern which contains all segments of `SEVEN`
* `SIX` is the only remaining pattern

#### Determine the unscrambled display values

* for a display value, find the deduced pattern which:
    * has the same length
    * has the same segments

### Day 9 - Smoke Basin

#### Part 1

For each coordinate:

* Fetch the neighbours
    * When the coordinate has 0 of max value for X or Y:  
      Filter the lower/higher X/Y values
* Fetch the heights of each neighbour
* Determine lowest neighbouring height
* Retain coordinates with lower height than **all** of its neighbours  
  Height should be lower, not lower or equal

#### Part 2

* Fetch the lowest points from the Part 1
* For each point: (1)
    * add the point to a Set
    * retrieve all its neighbours
        * filter points with height > original point and height < 9
        * recurse to point (1):  
          Recursion will stop whenever an edge point (height 9) occurs

### Day 10 - Syntax Scoring

#### Part 1

* Loop through the characters on each line
* If the next character is:
    * An opening character:  
      Push it to a stack
    * A closing character:  
      If the character matches the last opening character on the stack:
        * pop the last character from the stack
        * else, return the character  
          This is the first illegal character on that line.
* Map each illegal character to its score and reduce to their sum

##### `Stack` in Kotlin

There is no `Stack` in Kotlin, but a `MutableList` can be used for emulating this behaviour:

| `Stack` | `MutableList` |
|---------|---------------|
| `#push` | `#add`        |  
| `#pop`  | `#removeLast` |
| `#peek` | `#last`       |

#### Part 2

* Build the same stack as in Part 1, but:
    * Drop the code line when an illegal character occurs, since this is not a autocomplete error
* The remaining entries for each line, are opening characters with missing closing characters

### Day 11 - Dumbo Octopus

#### Part 1

Iterate `100` times over the grid of octopuses. For each iteration:

* Increase all energy levels
* Generate a `Sequence<Int>`, for each item in the sequence:
    * Count the number of octopuses with `energyLevel == 10`
    * For each octopus with `energyLevel == 10`:
        * Increment `energyLevel`  (will be `> 10`)
        * For each neighbour with `energyLevel != 10`: Increment `energyLevel`

  This `Sequence` will propagate all flashes, until an octopus has either flashes (`energyLevel > 10`) or will not flash in this iteration (`energyLevel < 10`).
  It stops when the `Sequence` generator return zero.
* Sum the result of the `Sequence`
* For each octopus that flashed (`energyLevel > 10`): Reset `energyLevel` to zero

##### Useful docs/links:

* [Sequences](https://kotlinlang.org/docs/sequences.html)

#### Part 2

* Generate a `Sequence` from the iteration in _Part 1_
* Keep generating while the result of one iteration `< 100`
* Count the number of iterations, incrementing it by one, since that iteration is the first to reach 100 flashes

### Day 12 - Passage Pathing

#### Part 1

* Build a `Map` which links each `Cave` to all directly accessible caves  
  Drop the `start`-`Cave` from all accessible caves
* Make a list containing just the `start`-cave
* Iterate over this `List`: (1)
    * For each accessible cave from the last element in the list:  
      Create a new list, appending the accessible cave
    * If:
        * we appended the `end`

##### Useful docs/links:

#### Part 2

##### Useful docs/links:

### Day 13 -

#### Part 1

##### Useful docs/links:

#### Part 2

##### Useful docs/links:

### Day 14 -

#### Part 1

##### Useful docs/links:

#### Part 2

##### Useful docs/links: 