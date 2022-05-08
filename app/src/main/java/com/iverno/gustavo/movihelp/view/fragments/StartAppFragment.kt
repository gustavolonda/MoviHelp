package com.iverno.gustavo.movihelp.view.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer

import com.iverno.gustavo.movihelp.databinding.FragmentStartAppBinding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.lang.Exception
import androidx.navigation.fragment.findNavController
import com.iverno.gustavo.movihelp.bo.StatusResponseDomain.SUCCESSFUL
import com.iverno.gustavo.movihelp.db.AppDatabase
import com.iverno.gustavo.movihelp.viewmodel.TheMovieDBViewModel
import com.iverno.gustavo.movihelp.view.activities.MainActivity

/**
 * A simple [Fragment] subclass.
 * Use the [StartAppFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartAppFragment : Fragment() {
    private lateinit var parentActity: MainActivity
    private lateinit var binding: FragmentStartAppBinding
    private val viewModel: TheMovieDBViewModel by viewModels()
    private val compositeDisposableOnPause = CompositeDisposable()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartAppBinding.inflate(inflater,container, false)
        binding.actionStartSincro.observableClickListener().subscribe()
        var dataBaseInstance = AppDatabase.getDatabasenIstance(parentActity)
        viewModel?.setInstanceOfDb(dataBaseInstance)
        observerViewModel()
        viewModel.getTheMovieItemListFromDataBase()
        return binding.root
    }
    private fun observerViewModel() {
        viewModel?.getTheMoviedbLiveData()?.observe(parentActity, Observer { response ->
            if (response.status.equals(SUCCESSFUL)){
                Log.e("Gust", response.theMovieDBItemList?.size.toString())
                Log.e("Gust", response.currentPage.toString())
                Log.e("Gust", response.totalPages.toString())
                if (response.theMovieDBItemList?.size!! > 0){

                    findNavController().navigateUp()
                    val action =
                        com.iverno.gustavo.movihelp.view.fragments.StartAppFragmentDirections.actionStartAppFragmentToHomeFragment()
                    findNavController().navigate(action)
                }
            }


        })
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment.
         * @return A new instance of fragment StartAppFragment.
         */
        @JvmStatic
        fun newInstance() = StartAppFragment()
    }

    override fun onAttach(context: Context) {
        /**
         * Use this factory method to get parent activity
         */
        super.onAttach(context)
        try {
            parentActity = context as MainActivity
        }catch (e: Exception){
            e.fillInStackTrace()
        }
    }

    fun View.observableClickListener(): Observable<View> {
        val publishSubject: PublishSubject<View> = PublishSubject.create()
        this.setOnClickListener { v ->
            viewModel.saveDownloadedTheMovie(parentActity,1)

         }
        return publishSubject
    }

}