package com.glureau.unexpected.subscribe.ex2

import com.glureau.unexpected.log
import com.glureau.unexpected.logSeparation
import com.glureau.unexpected.waitForDisposableToTerminate

fun main() {
    val vm = ViewModel()

    logSeparation("subscribe 1")

    vm.networkRequest
        .subscribe {
            log("subscribe/onNext: $it")
        }
        .also { vm.fetch() }
        .waitForDisposableToTerminate()

    logSeparation("subscribe 2")

    vm.networkRequest
        .subscribe {
            log("subscribe/onNext: $it")
        }
        .also { vm.fetch() }
        .waitForDisposableToTerminate()
}
