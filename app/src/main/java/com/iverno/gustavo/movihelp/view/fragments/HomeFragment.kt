package com.iverno.gustavo.movihelp.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.iverno.gustavo.movihelp.R
import com.iverno.gustavo.movihelp.databinding.FragmentHomeBinding
import com.iverno.gustavo.movihelp.db.AppDatabase
import com.iverno.gustavo.movihelp.view.adapters.TheMovieDBItemDetailAdapter
import com.iverno.gustavo.movihelp.viewmodel.TheMovieDBViewModel
import com.iverno.gustavo.movihelp.view.activities.MainActivity
import io.reactivex.rxjava3.core.Observable


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    private lateinit var parentActity: MainActivity
    private lateinit var binding: FragmentHomeBinding
    private var theMivieDBType:Array<String> = emptyArray()
    private var theMivieDBCategory:Array<String> = emptyArray()
    private val viewModel: TheMovieDBViewModel by viewModels()
    private var theMovieDBItemDetailAdapter: TheMovieDBItemDetailAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container, false)
        // access the items of the list
        theMivieDBType               = resources.getStringArray(R.array.the_movie_type)
        binding.spinnerType.adapter  = ArrayAdapter(parentActity,
                                                    android.R.layout.simple_spinner_item,
                                                    theMivieDBType)

        theMivieDBCategory               = resources.getStringArray(R.array.the_movie_category)

        binding.spinnerCategory.adapter  = ArrayAdapter(parentActity,
                                                    android.R.layout.simple_spinner_item,
                                                    theMivieDBCategory)

        var dataBaseInstance = AppDatabase.getDatabasenIstance(parentActity)
        viewModel?.setInstanceOfDb(dataBaseInstance)
        observerViewModel()
        theMovieDBItemDetailAdapter = TheMovieDBItemDetailAdapter()
        binding.recyclerview.adapter = theMovieDBItemDetailAdapter

        viewModel.getTheMovieItemListFromDataBase()
        binding.swipeToRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {


                binding.swipeToRefresh.isRefreshing = false
            }
        })



        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         * @return A new instance of fragment HomeFragment.
         */

        @JvmStatic
        fun newInstance() = HomeFragment()
    }
    override fun onStart() {
        super.onStart()
        val searchTextObservable = onItemSelectedListenerObservable()
        searchTextObservable
            .subscribe { query ->
                Toast.makeText(parentActity, "Test"+query.toString(), Toast.LENGTH_LONG).show()

            }
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
    private fun onItemSelectedListenerObservable(): Observable<String> {
        return Observable.create { emitter ->
                                        binding.spinnerType.onItemSelectedListener  = object :
                                            AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(
                                                parent: AdapterView<*>?,
                                                view: View?, position: Int, id: Long
                                            ) { emitter.onNext(theMivieDBType[position]) }

                                            override fun onNothingSelected(parent: AdapterView<*>) {
                                                // write code to perform some action
                                            }
                                        }
                                        emitter.setCancellable {
                                            binding.spinnerType.onItemSelectedListener = null
                                        }
        }
    }

    private fun observerViewModel() {
        viewModel?.getTheMoviedbLiveData()?.observe(parentActity, Observer { response ->
            response.theMovieDBItemList?.let { theMovieDBItemDetailAdapter?.setListItem(it) }
        })
    }


}



