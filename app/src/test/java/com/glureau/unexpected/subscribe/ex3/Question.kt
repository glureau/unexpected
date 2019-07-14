package com.glureau.unexpected.subscribe.ex3

import com.glureau.unexpected.log
import com.glureau.unexpected.waitForDisposableToTerminate

fun main() {
    val vm = ViewModel()

    vm.subject
        .take(2)
        .subscribe {
            log("subscribe/onNext: $it")
        }
        .also { vm.fetch() }
        .waitForDisposableToTerminate()
}
