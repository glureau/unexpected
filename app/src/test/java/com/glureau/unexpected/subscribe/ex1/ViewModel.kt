package com.glureau.unexpected.subscribe.ex1

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

class ViewModel {

    val networkRequest = PublishSubject.create<String>()

    fun fetch() {
        Observable.timer(100, TimeUnit.MILLISECONDS)
            .observeOn(Schedulers.io())
            .subscribe {
                networkRequest.onNext("item")
                networkRequest.onComplete()
            }
    }
}