package com.glureau.unexpected.observeOn.ex2

import com.glureau.unexpected.log
import com.glureau.unexpected.waitForDisposableToTerminate
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun main() {
    Observable.fromCallable { return@fromCallable "stuff" }
        .doOnNext { log("doOnNext 1: $it") }
        .observeOn(Schedulers.single())
        .doOnNext { log("doOnNext 2: $it") }
        .observeOn(Schedulers.computation())
        .doOnNext { log("doOnNext 3: $it") }
        .observeOn(Schedulers.io())
        .doOnNext { log("doOnNext 4: $it") }

        .subscribe {
            log("subscribe/onNext: $it")
        }
        .waitForDisposableToTerminate()
}
