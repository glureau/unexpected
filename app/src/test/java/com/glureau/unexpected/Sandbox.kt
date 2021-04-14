package com.glureau.unexpected

var count = 0
fun main() {
    val threads = (0..99).map {
        Thread {
            repeat(1000) {
                count++
            }
        }.apply {
            start()
        }
    }
    threads.forEach { it.join() }
    println("count=$count")
}

fun update() {
    count++
    storeCountOnDisk(count)
}

var diskCount = 0
fun storeCountOnDisk(count: Int) {
    Thread.sleep(1)
    diskCount = count
}

fun doSomething() {

    Thread.sleep(100)// Waiting for stream to complete
}

fun retrieveDataFromStorage(): Int {
    if (Thread.currentThread().name.contains("RxCachedThreadScheduler"))
        return count
    else
        TODO()
}

fun handleCount(c: Int) {
    log("handle $c")
}
/*
fun doSomething(): String {
    Thread.sleep(50)
    log("doSomething")
    return "something"
}
*/