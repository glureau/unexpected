package com.glureau.unexpected.badpractice

import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.lang.Thread.sleep

fun main() {
    val networkSubject = BehaviorSubject.createDefault(false)

    println("Thread: ${Thread.currentThread().name}")
    networkSubject
        .subscribeOn(Schedulers.io())
        .subscribe {
            println("data: $it on thread: ${Thread.currentThread().name}")
        }

    sleep(100)

    networkSubject.onNext(true)
}

// On which thread do we print the data?

// It depends

// The subscribeOn only impacts the initial subscription and does NOT impact the future items in a stream.
// Here the subscription is hidden in the createDefault method, and will basically emit "false" on this thread.
// So the 1st item is going on an IO thread.
// After that point, the IO thread is not used anymore, the onNext is called on main thread,
// so the 2nd println() will be on the main thread.

// Timing is super crucial in this case, if the onNext is played immediately after (without the sleep),
// then the thread switching taking some time will actually emit the 2nd items on the initial IO thread.

// Basic Rules :
// - use subscribeOn() only after a stream creation from a callback (Observable.fromCallable { emitter -> ... }.subscribeOn(IO))
// - use observeOn() for all other thread requirements