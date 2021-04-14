package com.glureau.unexpected.badpractice

import com.glureau.unexpected.waitForDisposableToTerminate
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.lang.Thread.sleep

fun intensiveCompute(v: Int): Int {
    println("Computing on thread ${Thread.currentThread().name}")
    sleep(300)
    return v * v
}

fun main() {
    Observable.fromIterable(0 until 20)
        .observeOn(Schedulers.io())
        .map { v -> intensiveCompute(v) } // We want that to be multi-threaded
        .toList()
        .subscribe { list ->
            println("Received $list on thread ${Thread.currentThread().name}")
        }
        .waitForDisposableToTerminate()
}
// map -> does NOT dispatch on multiple thread
// Fix: replace map with flatMap
//.flatMapSingle { v ->
//    Single.create<Int> { emitter ->
//        emitter.onSuccess(intensiveCompute(v))
//    }.subscribeOn(Schedulers.io())
//}
