package com.glureau.unexpected

import io.reactivex.Observable

fun main() {
    Observable.fromCallable {
        log("preparing...")
        Thread.sleep(300)
        log("preparation done")
        return@fromCallable "stuff"
    }
        .doOnNext { log("doOnNext: $it") }
        .subscribe {
            log("subscribe/onNext: $it")
        }
        .waitForDisposableToTerminate()
}
