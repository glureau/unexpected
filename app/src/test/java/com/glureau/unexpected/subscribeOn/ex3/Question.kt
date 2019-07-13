package com.glureau.unexpected.subscribeOn.ex3

import com.glureau.unexpected.log
import com.glureau.unexpected.logSeparation
import com.glureau.unexpected.waitForDisposableToTerminate
import io.reactivex.schedulers.Schedulers

fun main() {
    val vm = ViewModel()
    logSeparation("Observable1")
    vm.providesObservable1()
        .doOnNext { log("doOnNext: $it") }
        .subscribeOn(Schedulers.computation())
        .subscribe {
            log("subscribe/onNext: $it")
        }
        .waitForDisposableToTerminate()

    logSeparation("Observable2")
    vm.providesObservable2()
        .doOnNext { log("doOnNext: $it") }
        .subscribeOn(Schedulers.computation())
        .subscribe {
            log("subscribe/onNext: $it")
        }
        .waitForDisposableToTerminate()
}
