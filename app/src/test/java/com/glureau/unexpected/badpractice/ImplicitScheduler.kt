package com.glureau.unexpected.badpractice

import com.glureau.unexpected.waitForDisposableToTerminate
import io.reactivex.Completable
import java.util.concurrent.TimeUnit

fun printThreadName() = println(Thread.currentThread().name)

fun main() {
    printThreadName()
    Completable.timer(1, TimeUnit.SECONDS)
        .subscribe { printThreadName() }
        .waitForDisposableToTerminate()
}

// Some methods defines "default" schedulers.
// Given a scheduler can be a single thread or a pool of thread (concurrent/serialized),
// the behaviour can be impacted by those methods.
// Usually in RxJava the time related methods are subject to default schedulers
// timer/interval/delay/...
// You can define the scheduler as the 3rd parameter
// Completable.timer(1, TimeUnit.SECONDS, Schedulers.computation())