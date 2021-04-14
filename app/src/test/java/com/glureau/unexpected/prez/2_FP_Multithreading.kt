package com.glureau.unexpected.prez

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

private fun doPureMagic(input: Int): Int {
    return input + 2
}

private fun doPureMagicRx(): Single<Int> {
    return Single.create<Int> {
        var localCounter = 0
        for (j in 0 until 1000) {
            localCounter = doPureMagic(localCounter)
        }
        it.onSuccess(localCounter)
    }.subscribeOn(Schedulers.io())
}

fun computeFp(): Maybe<Int> {
    return Observable.fromIterable(0 until 100)
        .subscribeOn(Schedulers.io())
        .flatMapSingle { doPureMagicRx() }
        .reduce { t1, t2 -> t1 + t2 }// merge all streams in one final result
}

fun main() {
    computeFp().subscribe { result ->
        println("Total: $result")
    }
    Thread.sleep(1000)

    // In pure FP, there is no "memory", no side-effect, no need for synchronization.
    // You can NOT have a "cache issue" or a "it happens after some times", "it's ok on my device"..
    // But Java/Kotlin/C/Swift are not FP languages, so it's pretty hard to do.
    // Rx tries to mix both worlds... but we have to understand them.
}

