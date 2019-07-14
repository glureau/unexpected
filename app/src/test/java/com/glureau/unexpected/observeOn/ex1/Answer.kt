package com.glureau.unexpected.observeOn.ex1

import com.glureau.unexpected.log
import com.glureau.unexpected.waitForDisposableToTerminate
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun main() {
    Observable.fromCallable {
        log("preparing...")
        Thread.sleep(300)
        log("preparation done")
        return@fromCallable "stuff"
    }
        .doOnNext { log("doOnNext: $it") }

        // Change the thread for the downstream part only, so position matters
        .observeOn(Schedulers.computation())

        // Change the 'initial' thread, here the thread calling the Callable.call().
        .subscribeOn(Schedulers.io())

        .subscribe {
            log("subscribe/onNext: $it")
        }
        .waitForDisposableToTerminate()
}
