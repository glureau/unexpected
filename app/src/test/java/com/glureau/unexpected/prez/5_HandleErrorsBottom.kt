package com.glureau.unexpected.prez

import com.glureau.unexpected.badpractice.mvvm.sendThirdPartyService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.lang.Thread.sleep


// Returns true if the email has been sent
private fun sendEmail(msg: String): Single<Boolean> {
    return Single.create<Boolean> {
        val sent = sendThirdPartyService(msg, "EMAIL")
        it.onSuccess(sent)
    }.subscribeOn(Schedulers.io())
}

// Returns true if the SMS has been sent
private fun sendSms(msg: String): Single<Boolean> {
    return Single.create<Boolean> {
        val sent = sendThirdPartyService(msg, "SMS")
        it.onSuccess(sent)
    }.subscribeOn(Schedulers.io())
}

// At least one message is sent
private fun sendMessageToCustomer(msg: String): Single<Boolean> {
    // mergeDelayError : will wait for both Single to be finished, and will delay errors after the booleans
    return Single.mergeDelayError(sendEmail(msg), sendSms(msg))
        // firstElement: transform a stream to a
        .firstElement()// firstElement returns a Maybe
        .toSingle()
}

fun main() {
    sendMessageToCustomer("hello")
        .subscribe({ sent ->
            if (sent) {
                println("Message SENT!")
            } else {
                println("Message cannot be SENT!")
            }
        }, { error ->
        })
    sleep(1000)
}

