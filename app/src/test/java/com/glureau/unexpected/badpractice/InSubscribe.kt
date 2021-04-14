package com.glureau.unexpected.badpractice

import io.reactivex.Observable
import io.reactivex.Single

fun main() {
    if (true) {
        Observable.just(1, 0, -1)
            .subscribe(
                { item -> println("Data: ${20 / item}") }, // onNext
                { error -> println("Error: $error") }, // onError
                { println("Complete") }, // onComplete
                { println("Subscribed") }, // onSubscribe
            )
    } else {
        // New version, 1 and -1 are not wanted anymore, so transforming an Observable to Single
        Single.just(0)
            .subscribe(
                { item -> println("Data: ${20 / item}") }, // onSuccess
                { error -> println("Error: $error") }, // onError
            )
    }
}


// Observable.subscribe = 4 methods : onNext / onError / onComplete / onSubscribe
// Usually only the first 1 or 2 are implemented.
// The onNext lambda can still generate an error during execution, forwarded to the onError method

// Single.subscribe = 2 methods : onSuccess / onError
// The onSuccess is, like Observable onError/onComplete), a terminal event, so the onError will NOT be called !
//RxJavaPlugins.setErrorHandler {
//    println("Uncaught error: $it")
//}

// Changing the stream type is not straightforward