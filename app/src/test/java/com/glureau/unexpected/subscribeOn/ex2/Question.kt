package com.glureau.unexpected.subscribeOn.ex2

import com.glureau.unexpected.log
import com.glureau.unexpected.waitForDisposableToTerminate
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun main() {
    Observable.fromCallable { return@fromCallable "stuff" }
        .doOnNext { log("doOnNext: $it") }

        .subscribeOn(Schedulers.single())
        .subscribeOn(Schedulers.computation())
        .subscribeOn(Schedulers.io())

        .subscribe {
            log("subscribe/onNext: $it")
        }
        .waitForDisposableToTerminate()
}
