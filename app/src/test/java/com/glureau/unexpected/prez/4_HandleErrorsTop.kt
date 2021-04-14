package com.glureau.unexpected.prez

import com.glureau.unexpected.badpractice.mvvm.sendThirdPartyService
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import java.lang.Thread.sleep

// try/catch approach at the beginning of the stream work
// BUT it hides the error, making it harder to define a retry mechanism on the client side
// for example :
// - Does the device has network? If no, then retry later when network is back (high probability retry will work)
// - Does the servers responds a 500? If yes, then it could be useless to "retry" (low probability retry will work)
fun SAFEsendThirdPartyService(msg: String, technicalServiceName: String): Boolean {
    try {
        sendThirdPartyService(msg, "EMAIL")
        return true
    } catch (t: Throwable) {
        println("$technicalServiceName has failed")
        return false
    }
}

// Returns true if the email has been sent
private fun sendEmail(msg: String): Single<Boolean> {
    return Single.create<Boolean> {
        val sent = SAFEsendThirdPartyService(msg, "EMAIL")
        it.onSuccess(sent)
    }.subscribeOn(Schedulers.io())
}

// Returns true if the SMS has been sent
private fun sendSms(msg: String): Single<Boolean> {
    return Single.create<Boolean> {
        val sent = SAFEsendThirdPartyService(msg, "SMS")
        it.onSuccess(sent)
    }.subscribeOn(Schedulers.io())
}

// At least one communication
private fun sendMessageToCustomer(msg: String): Single<Boolean> {
    return Single.zip(sendEmail(msg), sendSms(msg), { emailOk, smsOk -> emailOk || smsOk })
}

fun main() {
    sendMessageToCustomer("hello")
        .subscribe { sent ->
            if (sent) {
                println("Message SENT!")
            } else {
                println("Message cannot be SENT!")
            }
        }
    sleep(1000)
}

