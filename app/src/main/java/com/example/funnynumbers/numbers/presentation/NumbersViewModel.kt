package com.example.funnynumbers.numbers.presentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.funnynumbers.numbers.domain.NumberFact
import com.example.funnynumbers.numbers.domain.NumberUiMapper
import com.example.funnynumbers.numbers.domain.NumbersInteractor
import com.example.funnynumbers.numbers.domain.NumbersResult

class NumbersViewModel(
    private val communications: NumbersCommunications,
    private val interactor: NumbersInteractor,
    private val mapper: NumberFact.Mapper<NumberUi> = NumberUiMapper()

) : ObserveNumbers, FetchNumbers {

    override fun observeProgress(owner: LifecycleOwner, observer: Observer<Boolean>) =
        communications.observeProgress(owner, observer)


    override fun observeState(owner: LifecycleOwner, observer: Observer<UiState>) =
        communications.observeState(owner, observer)


    override fun observeList(owner: LifecycleOwner, observer: Observer<List<NumberUi>>) =
        communications.observeList(owner, observer)

    override fun init(isFirstRun: Boolean) {
        if (isFirstRun) {
            communications.showProgress(true)
            viewModelScope.launch {
                val result = interactor.init()
                communications.showProgress(false)
                result.map(object : NumbersResult.Mapper<Unit> {
                    override fun map(list: List<NumberFact>, errorMessage: String) {
                        communications.showState(

                            if (errorMessage.isEmpty())
                                UiState.Success(list.map { it.map(mapper) })
                            else
                                UiState.Error(errorMessage)
                        )
                    }
                })
            }
        }
    }

    override fun fetchRandomNumberFact() {
        TODO("Not yet implemented")
    }

    override fun fetchNumberFact(number: String) {
        TODO("Not yet implemented")
    }

}

interface FetchNumbers {
    fun init(isFirstRun: Boolean)
    fun fetchRandomNumberFact()
    fun fetchNumberFact(number: String)
}