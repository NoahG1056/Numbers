package com.example.funnynumbers.numbers.domain

import com.example.funnynumbers.numbers.presentation.Mapper
import com.example.funnynumbers.numbers.presentation.NumberUi

data class NumberFact(
    private val id: String,
    private val fact: String
) {
    interface Mapper<T>{
        fun map(id:String,fact:String):T
    }
    fun <T> map(mapper:Mapper<T>):T=mapper.map(id,fact)

}

class NumberUiMapper:NumberFact.Mapper<NumberUi>{
    override fun map(id: String, fact: String): NumberUi=NumberUi(id,fact)

}