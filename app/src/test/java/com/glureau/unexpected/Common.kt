package com.glureau.unexpected.badpractice.mvvm

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import kotlin.random.Random

interface ViewInterface {
    fun onValueChanged(str: String)
}

class User(view: ViewInterface) {
    private val userInput = arrayOf("1", "12", "123", "1234")

    init {
        // Simulate the input of a user in a field
        Observable.interval(400, TimeUnit.MILLISECONDS)
            .subscribe {
                val currentUserInput = userInput[it.toInt() % 4]
                view.onValueChanged(currentUserInput)
            }
    }
}


class NetworkRepository {
    fun validateIban(iban: String): Single<Boolean> =
        Single.create<Boolean> { emitter ->
            // Simulate the network delay
            Thread.sleep(Random.nextLong(10, 400))

            emitter.onSuccess(iban == "1234")
        }.subscribeOn(Schedulers.io())
}


class FileRepository {
    fun restoreValue() = "iban"
    fun saveValue(iban: String): Completable =
        Completable.create { emitter ->
            // Simulate the disk delay
            Thread.sleep(Random.nextLong(1, 100))

            emitter.onComplete()
        }.subscribeOn(Schedulers.io())
}

class Analytics {
    fun trackIbanValidResult(isIbanValid: Boolean): Completable =
        Completable.create {
            println("# reportIbanValidResult $isIbanValid")
        }.subscribeOn(Schedulers.io())
}

// You do not own that code
fun sendThirdPartyService(msg: String, technicalServiceName: String): Boolean {
    Thread.sleep(Random.nextLong(300))
    if (Random.nextInt(3) == 0) { // 1/4 call will fail
        // Sometimes a service is down, a phone has no network, a DB cannot be reached...
        throw IllegalStateException("ERROR: Cannot send $technicalServiceName: $msg")
    } else {
        return true
    }
}