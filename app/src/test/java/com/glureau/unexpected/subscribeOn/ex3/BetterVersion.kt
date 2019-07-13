package com.glureau.unexpected.subscribeOn.ex3

import com.glureau.unexpected.log
import com.glureau.unexpected.logSeparation
import com.glureau.unexpected.waitForDisposableToTerminate
import io.reactivex.schedulers.Schedulers

/**
 * So if the position between subscribeOn matters, and calls are chained through multiple classes,
 * it's IMPOSSIBLE to ensure the subscribeOn will be effective or ignored when used in the downstream.
 *
 * And since the consumer of a stream can't know if the provider use subscribeOn or not, it should not try to interfere.
 *
 * If you want to ensure the downstream is in the right thread, use observeOn instead of subscribeOn.
 *
 * As a consumer you should not be worried about the provider thread management, you're only the consumer, stay in your scope.
 */
fun main() {
    val vm = ViewModel()
    logSeparation("Observable1")
    vm.providesObservable1()
        .observeOn(Schedulers.computation())
        .doOnNext { log("doOnNext: $it") }
        .subscribe {
            log("subscribe/onNext: $it")
        }
        .waitForDisposableToTerminate()

    logSeparation("Observable2")
    vm.providesObservable2()
        .observeOn(Schedulers.computation())
        .doOnNext { log("doOnNext: $it") }
        .subscribe {
            log("subscribe/onNext: $it")
        }
        .waitForDisposableToTerminate()
}
