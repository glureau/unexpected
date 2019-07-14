package com.glureau.unexpected.subscribe.ex1

import com.glureau.unexpected.log

import com.glureau.unexpected.waitForDisposableToTerminate

fun main() {
    val vm = ViewModel()
    vm.networkRequest
        .subscribe {
            log("subscribe/onNext: $it")
        }
        .also { vm.fetch() }
        .waitForDisposableToTerminate()

}
