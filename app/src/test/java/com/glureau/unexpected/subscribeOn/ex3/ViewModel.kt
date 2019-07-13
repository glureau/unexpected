package com.glureau.unexpected.subscribeOn.ex3

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers


class ViewModel {
    fun providesObservable1() = Observable.fromCallable { "obs1 result" }
    fun providesObservable2() = Observable.fromCallable { "obs2 result" }
        .subscribeOn(Schedulers.newThread())
}