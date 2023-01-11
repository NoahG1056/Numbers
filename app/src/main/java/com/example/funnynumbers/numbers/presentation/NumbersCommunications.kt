package com.example.funnynumbers.numbers.presentation

interface NumbersCommunications {
    fun showProgress(show:Boolean)
    fun showState(state:UiState)
    fun showList(list:List<NumberUi>)

    class Base(
        private

    ):NumbersCommunications {
        override fun showProgress(show: Boolean) {
            TODO("Not yet implemented")
        }

        override fun showState(state: UiState) {
            TODO("Not yet implemented")
        }

        override fun showList(list: List<NumberUi>) {
            TODO("Not yet implemented")
        }
    }

}