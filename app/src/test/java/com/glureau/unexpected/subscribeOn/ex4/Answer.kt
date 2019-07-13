package com.glureau.unexpected.subscribeOn.ex4

import com.glureau.unexpected.log
import com.glureau.unexpected.waitForDisposableToTerminate
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

fun main() {
    Observable.fromCallable { return@fromCallable "stuff" }
        // Define your subscribeOn JUST AFTER the stream creation if you want the
        .subscribeOn(Schedulers.single())
        // Then you can use whatever you want, but do NOT re-use subscribeOn! (unless you want a really messy code)

        // The doOnSubscribe will be called on the thread you use when calling subscribe, so here it's the main thread
        .doOnSubscribe { log("doOnSubscribe 1") }
        .doOnNext { log("doOnNext: $it") }

        // Since there is no subscribeOn on the middle of your code,
        // you'll also have the GUARANTEE that the doOnSubscribe will be CALLED ON THE STREAM ORDER
        .doOnSubscribe { log("doOnSubscribe 2") }

        .subscribe {
            log("subscribe/onNext: $it")
        }
        .waitForDisposableToTerminate()
}
