package com.glureau.unexpected.subscribeOn.ex2

import com.glureau.unexpected.log
import com.glureau.unexpected.waitForDisposableToTerminate
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun main() {
    Observable.fromCallable { return@fromCallable "stuff" }
        .doOnNext { log("doOnNext: $it") }

        // First subscribeOn is executed,
        .subscribeOn(Schedulers.single())

        // The 2 other subscribeOn are simply ignored...
        .subscribeOn(Schedulers.computation())
        .subscribeOn(Schedulers.io())
        // so position between subscribeOn matters

        .subscribe {
            log("subscribe/onNext: $it")
        }
        .waitForDisposableToTerminate()
}
