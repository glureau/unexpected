package com.glureau.unexpected.observeOn.ex3

import com.glureau.unexpected.log
import com.glureau.unexpected.waitForDisposableToTerminate
import io.reactivex.Observable

fun main() {
    Observable.fromCallable { return@fromCallable "stuff" }
        .doOnNext { log("doOnNext 1: $it") }
        // When a thread switching occurs, 2 threads have to synchronize to share their local variables.
        // So switching from a thread to another has a cost.
        // On a powerful computer, switching 1000 threads 'only' costs 120~130ms, AND keep in mind that's a SUPER SIMPLE example
        // As a reminder 120~130ms = 8~9 frames lost (on a 60Hz device, 1 frame = 16ms)
        // Also our apps doesn't have just 1 Observable, and can have way more stuff to share.

        // So when you think "this map operation could be done in the computation scheduler",
        // try to guess if the operation will cost more or less than a thread switching.
        // Generally it's more advised to use it when doing CPU intensive stuff (image/video operations for example)
        .switchThread(1000)
        .doOnNext { log("doOnNext 2: $it") }

        // BUT when we have to switch to UI thread for example, and you don't know if the provider is already on UI
        // It costs almost nothing to add a duplicated observeOn(UI) mainly because it don't have to copy anything.
        .observeOnSameThreadAgain(1000)
        .doOnNext { log("doOnNext 3: $it") }

        .subscribe {
            log("subscribe/onNext: $it")
        }
        .waitForDisposableToTerminate()
}
