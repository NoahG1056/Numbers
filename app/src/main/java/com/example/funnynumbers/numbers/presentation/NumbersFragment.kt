package com.example.funnynumbers.numbers.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import com.example.funnynumbers.R
import com.example.funnynumbers.details.presentation.DetailsFragment
import com.example.funnynumbers.main.presentation.ShowFragment

class NumbersFragment:Fragment() {
    private var showFragment:ShowFragment=ShowFragment.Empty()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        showFragment =context as ShowFragment
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_numbers  ,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ProgressBar>(R.id.progressBar).visibility=View.GONE
        view.findViewById<View>(R.id.getFactButton).setOnClickListener {
            val detailsFragment=DetailsFragment.newInstance("Some information")

            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container,detailsFragment)
                .addToBackStack(detailsFragment.javaClass.simpleName)
                .commit()
         }
    }

    override fun onDetach() {
        super.onDetach()
        showFragment=ShowFragment.Empty()
    }

}