package com.glureau.unexpected.subscribe.ex4

import com.glureau.unexpected.log
import com.glureau.unexpected.logSeparation
import com.glureau.unexpected.waitForDisposableToTerminate

fun main() {
    val vm = ViewModel()

    logSeparation("subscribe 1")

    vm.potentialError
        .subscribe({
            log("subscribe/onNext: $it")
        }, {
            log("error ${it.message}")
        })
        .also { vm.fetch() }
        .waitForDisposableToTerminate()

    logSeparation("subscribe 2")

    vm.potentialError
        .subscribe({
            log("subscribe/onNext: $it")
        }, {
            log("error ${it.message}")
        })
        .also { vm.fetch() }
        .waitForDisposableToTerminate()
}
