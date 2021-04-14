package com.glureau.unexpected.prez


private class MagicComputer(initialValue: Int) {
    private var result = initialValue

    fun doMagic() {
        result += 2
    }

    fun getResult(): Int {
        return result
    }
}

fun compute_oop(): Int {
    val counter = MagicComputer(0)
    for (i in 0 until 100) {
        Thread {
            for (j in 0 until 1000) {
                counter.doMagic()
            }
        }.start()
    }
    return counter.getResult()
}

fun main() {
    Thread.sleep(1000)// Wait for all threads to end
    println(compute_oop())
    // Expected = 2 * 100 (threads) * 1000 (loop)
    // Actual = random
    // Possible fixes:
    // - use an AtomicInt
    // - use a synchronization block
    // Synchronization between threads -> performance impact

    // Handling the app state can be hard
    // Multi-threading issues look like: "I can't reproduce it, are you sure there is a bug?"
    // Eventually the bug ticket is dropped until it re-appears some weeks later for some "random" reasons.
}

/*
 *  fun doMagic() {
 *    counter += 2
 *  }
 * actually not "atomic", it's a series of instruction for the CPU
 *  fun doMagic() {
 *    counter = counter + 2
 *  }
 * will be translated in :
 * 1 - read the value in 'counter' memory
 * 2 - adds 2 to the value
 * 3 - set the result in 'counter' memory
 *
 * Since all threads can call the methods, the order of execution is not guaranteed.
 * In this case, if the timing is:
 * Thread A : read // 0
 * Thread A : adds // 0 + 2 = 2
 * Thread B : read // *0*
 * Thread A : set // counter <- 2
 * Thread B : adds // *0* + 2 = 2
 * Thread B : set(2) // counter <- 2
 */