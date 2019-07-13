package com.glureau.unexpected.subscribeOn.ex4

import com.glureau.unexpected.log
import com.glureau.unexpected.waitForDisposableToTerminate
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun main() {
    Observable.fromCallable { return@fromCallable "stuff" }
        .doOnNext { log("doOnNext: $it") }

        .doOnSubscribe { log("doOnSubscribe 1") }
        .subscribeOn(Schedulers.single())
        .doOnSubscribe { log("doOnSubscribe 2") }
        .subscribeOn(Schedulers.computation())
        .doOnSubscribe { log("doOnSubscribe 3") }
        .subscribeOn(Schedulers.io())
        .doOnSubscribe { log("doOnSubscribe 4") }

        .subscribe {
            log("subscribe/onNext: $it")
        }
        .waitForDisposableToTerminate()
}
