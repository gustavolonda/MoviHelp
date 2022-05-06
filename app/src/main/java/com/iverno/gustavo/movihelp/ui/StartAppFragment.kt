package com.iverno.gustavo.movihelp.ui

import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.iverno.gustavo.movihelp.databinding.FragmentStartAppBinding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.lang.Exception
import androidx.navigation.fragment.findNavController
import com.iverno.gustavo.movihelp.repository.TheMovieDBRepository
import com.iverno.gustavo.movihelp.bo.StatusResponseDomain.SUCCESSFUL
import com.iverno.gustavo.movihelp.db.AppDatabase

/**
 * A simple [Fragment] subclass.
 * Use the [StartAppFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartAppFragment : Fragment() {
    private lateinit var parentActity: MainActivity
    private lateinit var binding: FragmentStartAppBinding
    private val viewModel: TheMovieDBViewModel by activityViewModels()
    private val compositeDisposableOnPause = CompositeDisposable()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartAppBinding.inflate(inflater,container, false)
        binding.actionStartSincro.observableClickListener().subscribe()
        val theMovieDBRepository= TheMovieDBRepository()
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        var dataBaseInstance = AppDatabase.getDatabasenIstance(parentActity)
        viewModel?.setInstanceOfDb(dataBaseInstance)
        observerViewModel()
        viewModel.getTheMovieItemListFromDataBase()
        return binding.root
    }
    private fun observerViewModel() {
        viewModel?.getTheMoviedbLiveData()?.observe(parentActity, Observer {
            if (it.status.equals(SUCCESSFUL)){
                Log.e("Gust", it.theMovieDBItemList?.size.toString())
                Log.e("Gust", it.currentPage.toString())
                Log.e("Gust", it.totalPages.toString())
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
            /*val action =
                com.iverno.gustavo.movihelp.ui.StartAppFragmentDirections.actionStartAppFragmentToHomeFragment()
            findNavController().navigate(action)

            publishSubject.onNext(v)*/
            viewModel.saveDownloadedTheMovie(parentActity,1)
         }
        return publishSubject
    }

}