package com.iverno.gustavo.movihelp.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.view.View
import com.iverno.gustavo.movihelp.R
import com.iverno.gustavo.movihelp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }
    override fun onBackPressed(){
        this.finish()
    }
    public fun hideProgressBar(){
        if(binding != null) binding.progressBarLinear.visibility = View.GONE
    }
    public fun showProgressBar(){
        if(binding != null) binding.progressBarLinear.visibility = View.VISIBLE
    }
}