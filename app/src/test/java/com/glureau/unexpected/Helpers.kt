package com.glureau.unexpected

import io.reactivex.disposables.Disposable

var previousTime: Long? = null
fun log(msg: String) {
    val now = System.currentTimeMillis()
    val timeStr = if (previousTime == null) {
        "$now"
    } else {
        "$now (" + (now - (previousTime ?: 0L)) + "ms since the last log)"
    }
    previousTime = now
    val threadName = Thread.currentThread().name + "(" + Thread.currentThread().id + ")"
    println("$msg   - on $threadName at time $timeStr")
}

fun logSeparation(msg: String) {
    println("------------------------------------ $msg ------------------------------------")
}

fun Disposable.waitForDisposableToTerminate() {
    while (!isDisposed) {
        Thread.sleep(100)
    }
}
