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
        .subscribeOn(Schedulers.computation()) // This line is WORKING because the obs1 do NOT use subscribeOn
        .subscribe {
            log("subscribe/onNext: $it")
        }
        .waitForDisposableToTerminate()

    logSeparation("Observable2")
    vm.providesObservable2()
        .doOnNext { log("doOnNext: $it") }
        .subscribeOn(Schedulers.computation()) // This line is NOT WORKING because the obs1 ALREADY USE subscribeOn
        .subscribe {
            log("subscribe/onNext: $it")
        }
        .waitForDisposableToTerminate()
}
