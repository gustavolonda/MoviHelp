package com.iverno.gustavo.movihelp.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import com.iverno.gustavo.movihelp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }
    override fun onBackPressed(){
        this.finish()
    }
}