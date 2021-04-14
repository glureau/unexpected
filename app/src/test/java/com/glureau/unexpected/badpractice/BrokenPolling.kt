package com.glureau.unexpected.badpractice

import com.glureau.unexpected.waitForDisposableToTerminate
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class Polling {
    val dataStream = Observable.interval(100, TimeUnit.MILLISECONDS)
        .flatMap { fetchData() }

    // Method that could fail, because I/O related (network/disk/...)
    private fun fetchData(): Observable<String> {
        val data = Random.nextInt() / Random.nextInt(10)
        return Observable.just(data.toString())
    }

    // Method that could fail, because I/O related (network/disk/...)
    private fun fetchData2(): Observable<String> {
        return Observable.fromCallable {
            Random.nextInt() / Random.nextInt(10)
        }.map { it.toString() }
            .subscribeOn(Schedulers.io())
    }
}

fun main() {
    Polling().dataStream.subscribe {
        println("Data: $it")
    }
        .waitForDisposableToTerminate()
}
