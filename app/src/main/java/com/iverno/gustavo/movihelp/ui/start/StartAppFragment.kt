package com.iverno.gustavo.movihelp.ui.start

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iverno.gustavo.movihelp.ui.MainActivity
import com.iverno.gustavo.movihelp.databinding.FragmentStartAppBinding
import com.iverno.gustavo.movihelp.ui.home.HomeFragment
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.lang.Exception
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass.
 * Use the [StartAppFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartAppFragment : Fragment() {
    private var parentActity: MainActivity? = null
    private lateinit var binding: FragmentStartAppBinding
    private val compositeDisposableOnPause = CompositeDisposable()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartAppBinding.inflate(inflater,container, false)
        binding.actionStartSincro.observableClickListener().subscribe()
        return binding.root
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
            parentActity = context as MainActivity?
        }catch (e: Exception){
            e.fillInStackTrace()
        }
    }

    fun View.observableClickListener(): Observable<View> {
        val publishSubject: PublishSubject<View> = PublishSubject.create()
        this.setOnClickListener { v ->
            val action = StartAppFragmentDirections.actionStartAppFragmentToHomeFragment()
            findNavController().navigate(action)

            publishSubject.onNext(v)
         }
        return publishSubject
    }

}