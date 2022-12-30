package com.example.funnynumbers.details.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.funnynumbers.R

class DetailsFragment:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details,container,false)
    }


    companion object{
        private const val KEY="DETAILS"

        fun newInstance(value:String)=
            DetailsFragment().apply {
                arguments=Bundle().apply {
                    putString("details",value)
                }
            }
        }

}