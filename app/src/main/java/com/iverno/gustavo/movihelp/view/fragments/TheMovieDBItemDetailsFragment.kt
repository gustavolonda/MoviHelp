package com.iverno.gustavo.movihelp.view.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.iverno.gustavo.movihelp.R
import com.iverno.gustavo.movihelp.bo.StatusResponseDomain
import com.iverno.gustavo.movihelp.databinding.FragmentTheMovieDBItemDetailsBinding
import com.iverno.gustavo.movihelp.db.AppDatabase
import com.iverno.gustavo.movihelp.view.activities.TheMovieDBItemDetailsActivity
import com.iverno.gustavo.movihelp.viewmodel.TheMovieDBItemDetailsViewModel
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass.
 * Use the [TheMovieDBItemDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TheMovieDBItemDetailsFragment : Fragment() {
    private var idItem = 0
    private lateinit var binding: FragmentTheMovieDBItemDetailsBinding
    private lateinit var parentActity: TheMovieDBItemDetailsActivity
    private val viewModel: TheMovieDBItemDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            idItem = it.getInt(ID_THE_MOVIE_ITEM)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTheMovieDBItemDetailsBinding.inflate(inflater,container, false)

        return binding.root
    }


    override fun onActivityCreated(bundle: Bundle?) {
        super.onActivityCreated(bundle)
        // Setting the Toolbal.
        if(parentActity != null){
            parentActity.setSupportActionBar(binding.toolbar);
            setHasOptionsMenu(true);
        }
        var dataBaseInstance = AppDatabase.getDatabasenIstance(parentActity)
        viewModel?.setInstanceOfDb(dataBaseInstance)
        observerViewModel(idItem)

    }

    override fun onAttach(context: Context) {
        /**
         * Use this factory method to get parent activity
         */
        super.onAttach(context)
        try {
            parentActity = context as TheMovieDBItemDetailsActivity
        }catch (e: Exception){
            e.fillInStackTrace()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                if(parentActity!=null) parentActity.finish();
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val  ID_THE_MOVIE_ITEM = "IdTheMovieItem"
        const val  PARAMS = "params"
        @JvmStatic
        fun newInstance(idItem: Int) =
            TheMovieDBItemDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ID_THE_MOVIE_ITEM, idItem)
                }
            }
    }
    private fun observerViewModel(idItem: Int) {
        viewModel?.getTheMovieDBItemLiveData(idItem)?.observe(parentActity, Observer { response ->
            if (response.status.equals(StatusResponseDomain.SUCCESSFUL)){
                binding.mViewModel = response
                response.theMovieDBItem?.let { downloadImage(it.getPosterUrlString()) }
            }

        })
    }
    private fun downloadImage(imageUrl: String){
        val picasso = Picasso.get()
        picasso.load(imageUrl)
                .error(R.drawable.ic_error_24)
                .placeholder(R.drawable.no_image)
                .into(binding.imageMovie)
    }
}