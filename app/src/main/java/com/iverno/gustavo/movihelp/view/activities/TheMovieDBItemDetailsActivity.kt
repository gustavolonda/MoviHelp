package com.iverno.gustavo.movihelp.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.iverno.gustavo.movihelp.R
import com.iverno.gustavo.movihelp.databinding.ActivityTheMovieDbitemDetailsBinding
import com.iverno.gustavo.movihelp.view.fragments.TheMovieDBItemDetailsFragment

class TheMovieDBItemDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTheMovieDbitemDetailsBinding
    private var idItem = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTheMovieDbitemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getParameters()
        var bundle =  Bundle();
        bundle.putInt(TheMovieDBItemDetailsFragment.ID_THE_MOVIE_ITEM,idItem);
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_details) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.nav_graph_details_item,bundle)
    }
    private fun  getParameters() {
        var  parms = getIntent().getBundleExtra(TheMovieDBItemDetailsFragment.PARAMS)
        idItem = parms?.getInt(TheMovieDBItemDetailsFragment.ID_THE_MOVIE_ITEM,0) ?:0
    }
    override fun onBackPressed(){
        this.finish()
    }

}