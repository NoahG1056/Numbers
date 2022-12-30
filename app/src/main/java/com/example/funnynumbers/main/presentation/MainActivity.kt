package com.example.funnynumbers.main.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.funnynumbers.R
import com.example.funnynumbers.numbers.presentation.NumbersFragment

class MainActivity : AppCompatActivity(),ShowFragment {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState==null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.container,NumbersFragment())
                .commit()
        }
    }

    override fun show(fragment: Fragment) {
        show(fragment,true)
    }

    private fun show(fragment:Fragment,add:Boolean){
        val transaction = supportFragmentManager.beginTransaction()
        val container= R.id.container
        if (add)
            transaction.add(container,fragment)
            else
                transaction.replace(container,fragment)
        transaction.commit()
    }
}

interface ShowFragment{
    fun show(fragment:Fragment)
    class Empty:ShowFragment{
        override fun show(fragment: Fragment)=Unit

    }
}