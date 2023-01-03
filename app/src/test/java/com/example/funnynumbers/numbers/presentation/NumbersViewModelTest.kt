package com.example.funnynumbers.numbers.presentation

import junit.framework.Assert.assertEquals
import org.junit.Test

class NumbersViewModelTest {
    /**
     * Initioal test
     * At start fetch data and show it
     * then try to get some data
     * then re-init and wait for the result
     */

    @Test
    fun testInitAndReInit() {
        val communications = TestNumbersCommunications()
        val interactor = TestNumbersInteractor()
        val viewModel = NumbersViewModel(communications, interactor)

        interactor.changeExpectedResult(NumbersResult.Success())

        viewModel.init(isFirstRun = true)

        assertEquals(1, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[0])

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success(), communications.stateCalledList[0])

        assertEquals(0, communications.numbersList.size)
        assertEquals(1, communications.timesShowList)


        //get some data

        interactor.changeExpectedResult(NumberResult.Failure())
        viewModel.fetchRandomNumberData()
        assertEquals(3, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[2])

        assertEquals(1,interactor.fetchAboutRandomNumberCalledList.size)

        assertEquals(4, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[3])

        assertEquals(2, communications.stateCalledList.size)
        assertEquals(UiState.Error(/* todo message */), communications.stateCalledList[0])
        assertEquals(1, communications.stateCalledList.size)


        viewModel.init(isFirstRun = false)

        assertEquals(4, communications.progressCalledList.size)
        assertEquals(2, communications.stateCalledList.size)
        assertEquals(1, communications.stateCalledList.size)



    }

    @Test
    fun factAboutEmptyNumber (){
        val communications = TestNumbersCommunications()
        val interactor = TestNumbersInteractor()
        val viewModel = NumbersViewModel(communications, interactor)

        viewModel.fetchFact("")

        assertEquals(1, interactor.fetchAboutNumberCalledList.size)

        assertEquals(0, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Error("entered number is empty"), communications.stateCalledList[0])

        assertEquals(0, communications.numbersList.size)
        assertEquals(1, communications.timesShowList)



    }

    @Test
    fun factAboutSomeNumber (){
        val communications = TestNumbersCommunications()
        val interactor = TestNumbersInteractor()
        val viewModel = NumbersViewModel(communications, interactor)

        interactor.changeExpectedResult(NumbersResult.Success(listOf(Number("45", "random fact about 45"))))
        viewModel.fetchFact("45")

        assertEquals(1, communications.progressCalledList.size)
        assertEquals(true, communications.progressCalledList[0])

        assertEquals(1,interactor.fetchAboutNumberCalledList.size)
        assertEquals(Number("45","random fact about 45"),interactor.fetchAboutNumberCalledList[0])

        assertEquals(2, communications.progressCalledList.size)
        assertEquals(false, communications.progressCalledList[1])

        assertEquals(1, communications.stateCalledList.size)
        assertEquals(UiState.Success(), communications.stateCalledList[0])

        assertEquals(1, communications.timesShowList)
        assertEquals(NumberUi("45", "random fact about 45"), communications.numbersList[0])
    }

    private class TestNumbersCommunications : NumbersCommunications {

        val progressCalledList = mutableListOf<Boolean>()
        val stateCalledList = mutableListOf<Boolean>()
        var timesShowList = 0
        val numbersList = mutableListOf<NumberUi>()

        override fun showProgress(show: Boolean) {
            progressCalledList.add(show)
        }

        override fun showState(state: UiState) {
            stateCalledList.add(state)

        }

        override fun showList(list: List<NumberUi>) {
            timesShowList++
            numbersList.addAll(list)
        }

        fun timesProgressCalled() = progressCalledList.size
    }

    private class TestNumbersInteractor : NumbersInteractor {
        private var result: NumbersResult = NumbersResult.Success()
        val initCalledList = mutableListOf<NumbersResult>()
        val fetchAboutNumberCalledList = mutableListOf<NumbersResult>()
        val fetchAboutRandomNumberCalledList = mutableListOf<NumbersResult>()


        fun changeExpectedResult(newResult: NumbersResult) {
            result = newResult
        }


        override suspend fun init(): NumbersResult {
            initCalledList.add(result)
            return result
        }
        override suspend fun factAboutNumber(number:String):NumberResult{
            fetchAboutNumberCalledList.add()
            return result
        }

        override suspend fun factAboutRandomNumber():NumberResult{
            fetchAboutRandomNumberCalledList.add(result)
            return result
        }
    }
}