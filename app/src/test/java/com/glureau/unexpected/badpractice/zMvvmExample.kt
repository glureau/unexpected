package com.glureau.unexpected.badpractice

import com.glureau.unexpected.badpractice.mvvm.*
import io.reactivex.subjects.PublishSubject
import java.lang.Thread.sleep


class View(private val viewModel: ViewModel) : ViewInterface {
    init {
        viewModel.onDataChanged.subscribe {
            println("isLoading = ${viewModel.isLoading}")
            println("isValid = ${viewModel.isValid}")
            println("saveCount = ${viewModel.saveCount}")
            println("currentIban = ${viewModel.currentIban}")
        }
    }

    // Callback system when the value has changed
    override fun onValueChanged(str: String) {
        viewModel.onIbanChanged(str)
    }
}

class ViewModel(
    private val networkRepository: NetworkRepository,
    private val fileRepository: FileRepository,
    private val analytics: Analytics
) {
    var isValid: Boolean = false
    var isLoading: Boolean = false
    var saveCount: Int = 0
    var currentIban: String = ""

    val onDataChanged = PublishSubject.create<Unit>()

    fun onIbanChanged(currentUserInput: String) {
        println("user input: $currentUserInput")

        currentIban = currentUserInput

        // Display the loader
        isLoading = true

        // Trigger the update of the view
        onDataChanged.onNext(Unit)

        networkRepository.validateIban(currentUserInput)
            .subscribe { isIbanValid ->
                // Display if the iban is eventually valid
                isLoading = false
                isValid = isIbanValid

                // Trigger the update of the view
                onDataChanged.onNext(Unit)

                // Track API result for future debugging
                analytics.trackIbanValidResult(isIbanValid).subscribe()
            }

        fileRepository.saveValue(currentUserInput).subscribe {
            saveCount++
        }
    }
}

fun main() {
    val repository = NetworkRepository()
    val viewModel = ViewModel(repository, FileRepository(), Analytics())
    val view = View(viewModel)
    val user = User(view)

    // Stop the simulation after 3s
    sleep(3000)
}

// Possible issues :
// - leak : the view knows about the lifecycle and should be able to dispose the stream, or the VM have to be lifecycle aware
// - consistency : streams to store the value is not flatMap-ed with the network call, so we can have desynchronization
//                 if an error occurs in one of the 2 streams
// - UI interactions have to be done on main thread
// - UI interactions can be faster than the network/IO duration ->
