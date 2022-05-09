package com.iverno.gustavo.movihelp.view.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.iverno.gustavo.movihelp.R
import com.iverno.gustavo.movihelp.bo.StatusResponseDomain
import com.iverno.gustavo.movihelp.bo.StatusResponseDomain.SUCCESSFUL
import com.iverno.gustavo.movihelp.config.Constants
import com.iverno.gustavo.movihelp.data.TheMovieDBItemListener
import com.iverno.gustavo.movihelp.data.TheMoviedbItem
import com.iverno.gustavo.movihelp.databinding.FragmentHomeBinding
import com.iverno.gustavo.movihelp.db.AppDatabase
import com.iverno.gustavo.movihelp.view.adapters.TheMovieDBItemDetailAdapter
import com.iverno.gustavo.movihelp.viewmodel.TheMovieDBViewModel
import com.iverno.gustavo.movihelp.view.activities.MainActivity
import com.iverno.gustavo.movihelp.view.activities.TheMovieDBItemDetailsActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit


/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), TheMovieDBItemListener {
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

        return binding.root
    }

    override fun onActivityCreated(bundle: Bundle?) {
        super.onActivityCreated(bundle)

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
        theMovieDBItemDetailAdapter = TheMovieDBItemDetailAdapter(this)
        binding.recyclerview.adapter = theMovieDBItemDetailAdapter
        binding.swipeToRefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener{
            override fun onRefresh() {
                viewModel.setIsDoewnloading(true)
                viewModel.saveDownloadedTheMovie(parentActity,viewModel.getPageCurrent(),viewModel.getTextSearch(),viewModel.getTypeSearch(),viewModel.getCategorySearch())
            }
        })

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
        val typeTextObservable = typeSearchObservable()
        typeTextObservable
            .subscribe { type ->
                viewModel.setTypeSearch(type)
                viewModel.getTheMovieItemListByQuery(0,0,viewModel.getTextSearch(),type,viewModel.getCategorySearch())

            }

        val categorySearchObservable = categorySearchObservable()
        categorySearchObservable
            .subscribe { category ->
                viewModel.setCategorySearch(category)
                viewModel.getTheMovieItemListByQuery(0,0,viewModel.getTextSearch(),viewModel.getTypeSearch(),category)

            }

        val searchText = onQueryTextChangeObservable()
        searchText.debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ text ->
                viewModel.setTextSearch(text)
                viewModel.getTheMovieItemListByQuery(0,0,text,viewModel.getTypeSearch(),viewModel.getCategorySearch())
            })
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
    private fun typeSearchObservable(): Observable<String> {
        return Observable.create { emitter ->
                                        binding.spinnerType.onItemSelectedListener  = object :
                                            AdapterView.OnItemSelectedListener {
                                            override fun onItemSelected(
                                                parent: AdapterView<*>?,
                                                view: View?, position: Int, id: Long
                                            ) {
                                                binding.swipeToRefresh.isRefreshing = true
                                                emitter.onNext(theMivieDBType[position])
                                            }

                                            override fun onNothingSelected(parent: AdapterView<*>) {
                                                // write code to perform some action
                                            }
                                        }
                                        emitter.setCancellable {
                                            binding.spinnerType.onItemSelectedListener = null
                                        }
        }
    }

    private fun categorySearchObservable(): Observable<String> {
        return Observable.create { emitter ->
            binding.spinnerCategory.onItemSelectedListener  = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?, position: Int, id: Long
                ) {
                    binding.swipeToRefresh.isRefreshing = true
                    emitter.onNext(theMivieDBCategory[position])
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
            emitter.setCancellable {
                binding.spinnerCategory.onItemSelectedListener = null
            }
        }
    }

    private fun onQueryTextChangeObservable(): Observable<String> {
        return Observable.create { emitter ->
            binding.searchText.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    return false
                }
                override fun onQueryTextChange(newText: String): Boolean {
                    binding.swipeToRefresh.isRefreshing = true
                    emitter.onNext(newText)
                    return false
                }
            })

        }
    }
    private fun observerViewModel() {
        viewModel?.getTheMoviedbLiveData()?.observe(parentActity, Observer { response ->
            if (response.status.equals(SUCCESSFUL)){
                if (viewModel.isDoewnloading()){
                    viewModel.setIsDoewnloading(false)
                    if (response.totalPages > viewModel.getPageCurrent())
                        viewModel.setPageCurrent(viewModel.getPageCurrent()+1)
                } else
                    viewModel.setIsDoewnloading(false)
                response.theMovieDBItemList?.let { theMovieDBItemDetailAdapter?.setListItem(it) }
            }

            binding.swipeToRefresh.isRefreshing = false
        })
    }

    override fun onClick(theMoviedbItem: TheMoviedbItem) {

        val intent = Intent(parentActity, TheMovieDBItemDetailsActivity::class.java)
        var parms =  Bundle()
        parms.putInt(TheMovieDBItemDetailsFragment.ID_THE_MOVIE_ITEM, theMoviedbItem.id);
        intent.putExtra(TheMovieDBItemDetailsFragment.PARAMS,parms)
        startActivity(intent)
    }

}



