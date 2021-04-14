package com.glureau.unexpected.prez

// Object Oriented Programming = stateful
private class Counter(initialValue: Int) {
    private var counter = initialValue

    // OOP method because it has a side effect
    fun increment() {
        counter++ // Side effect = "impure" function
    }

    // OOP method because it uses a local value
    fun getCounter(): Int {
        return counter
    }
}





// Functional Programming = stateless
private fun fpIncrement(count: Int): Int {
    return count + 1 // No side effect = "pure" function
}




fun main() {
    // OOP
    val counter = Counter(42)
    counter.increment()
    counter.increment()
    counter.increment()
    println(counter.getCounter())

    // FP
    println(fpIncrement(fpIncrement(fpIncrement(42))))

    // Concept of FP comes from Lambda calculus, Alonzo Church in 1930 (worked with Turing)
    // https://www.youtube.com/watch?v=eis11j_iGMs
    // Lambda as the greek letter λ, gives the name of our "lambda" in code '{ input -> println(input) }'
    // As it's usually an anonymous class with no members, it's "pure" function... (or almost)

    // Differences : https://drek4537l1klr.cloudfront.net/atencio/HighResolutionFigures/table_2-1.png

    val collection = listOf(1, 2, 3, 4, 5)
    // Imperative code: each step is defined:
    val even = mutableListOf<Int>() // How to store
    collection.forEach {
        if (it % 2 == 0) {
            even.add(it)
        }
    }

    // Declarative code: what do I want
    collection.where { it.isEven() }
    // ex: SQL is declarative
    // SELECT * FROM users WHERE id=42
}








// Ignore that
fun List<Int>.where(condition: (Int) -> Boolean) {}
fun Int.isEven() = this % 2 == 0