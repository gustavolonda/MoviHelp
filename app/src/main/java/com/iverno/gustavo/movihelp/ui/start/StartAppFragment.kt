package com.iverno.gustavo.movihelp.ui.start

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.iverno.gustavo.movihelp.ui.MainActivity
import com.iverno.gustavo.movihelp.databinding.FragmentStartAppBinding
import java.lang.Exception


/**
 * A simple [Fragment] subclass.
 * Use the [StartAppFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StartAppFragment : Fragment() {
    private var parentActity: MainActivity? = null
    private lateinit var binding: FragmentStartAppBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartAppBinding.inflate(inflater,container, false)
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
}