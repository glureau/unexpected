package com.glureau.unexpected.observeOn.ex3

import com.glureau.unexpected.log
import com.glureau.unexpected.waitForDisposableToTerminate
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun main() {
    Observable.fromCallable { return@fromCallable "stuff" }
        .doOnNext { log("doOnNext 1: $it") }
        .switchThread(1000)
        .doOnNext { log("doOnNext 2: $it") }
        .observeOnSameThreadAgain(1000)
        .doOnNext { log("doOnNext 3: $it") }

        .subscribe {
            log("subscribe/onNext: $it")
        }
        .waitForDisposableToTerminate()
}

fun <T> Observable<T>.switchThread(switchCount: Int): Observable<T> {
    var o = this
    for (i in 0..switchCount / 2) {
        o = o.observeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
    }
    return o
}

fun <T> Observable<T>.observeOnSameThreadAgain(observeOnCount: Int): Observable<T> {
    var o = this
    for (i in 0..observeOnCount) {
        o = o.observeOn(Schedulers.single())
    }
    return o
}
