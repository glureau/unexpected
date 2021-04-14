package com.glureau.unexpected.badpractice

import com.glureau.unexpected.waitForDisposableToTerminate
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

fun main() {
    val stream1 = Observable.just(true)
    val stream2 = PublishSubject.create<Boolean>()
    stream2.onNext(true)
    val stream3 = Observable.just(false, true, false, true)

    Observable.combineLatest(
        stream1,
        stream2,
        stream3,
        { p1, p2, p3 -> p1 || p2 || p3 })
        .subscribe { println("Result: $it") }
        .waitForDisposableToTerminate()
}











// Data of stream1 and 3 is never processed because the stream 2 does not emit anything AFTER the subscribe.
// Possible fixes :
// - Use Behaviour/Replay subjects, so that the last item(s) are replayed when subscribing
// - Use stream2.startWith(false) in the combineLatest if it makes sense
// - Good naming convention can help understand the issue