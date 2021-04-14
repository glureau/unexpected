package com.glureau.unexpected.badpractice

import io.reactivex.Observable


fun main() {
    computeStuff()
}

fun computeStuff(): Observable<Int> =
    Observable.just(1, 2, 3)
        .doOnNext { v -> println(v) }
        .map { v -> v * v }
        .filter { v -> v > 2 }















/*
 * Naming convention can help a lot even if it's not perfect.
 * On Android we generally uses:
 *
 * - 'Relay' suffix when the stream provided is not supposed to emit errors.
 * So when you consumes a Relay, you're not supposed to implement the onError,
 * it's supposed to be handled before this point.
 *
 * - 'Single'/'Completable'/... suffix when you provides the stream next to the latest value. Ex:
 * userManager.isLogged returns a boolean
 * userManager.loggedRelay returns an Observable of boolean
 *
 * Using a suffix for all relays can help spot a missing subscribe
 */