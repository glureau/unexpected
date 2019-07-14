package com.glureau.unexpected.subscribe.ex4

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class ViewModel {

    val potentialError = PublishSubject.create<String>()

    fun fetch() {
        Observable.timer(100, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.io())
            .subscribe {
                potentialError.onNext("item")
                potentialError.onError(Throwable("Bad luck"))
            }
    }
}