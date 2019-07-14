package com.glureau.unexpected.subscribe.ex3

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class ViewModel {

    val subject = BehaviorSubject.createDefault("")

    fun fetch() {
        Observable.interval(0, 100, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.io())
            .subscribe {
                subject.onNext("Item $it")
            }
    }
}