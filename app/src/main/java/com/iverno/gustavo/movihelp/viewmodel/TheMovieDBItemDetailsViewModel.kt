package com.iverno.gustavo.movihelp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iverno.gustavo.movihelp.bo.StatusResponseDomain
import com.iverno.gustavo.movihelp.data.TheMovieDBItemDetailsViewModelResponse
import com.iverno.gustavo.movihelp.db.AppDatabase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class TheMovieDBItemDetailsViewModel : ViewModel() {
    protected val compositeDisposable = CompositeDisposable()
    private var dataBaseInstance: AppDatabase?= null
    private var theMovieDBItemLiveData : MutableLiveData<TheMovieDBItemDetailsViewModelResponse>? = null
    fun setInstanceOfDb(dataBaseInstance: AppDatabase) {
        this.dataBaseInstance = dataBaseInstance
    }
    fun getTheMovieDBItemLiveData(idItem: Int): LiveData<TheMovieDBItemDetailsViewModelResponse> {
        if (theMovieDBItemLiveData == null) {
            theMovieDBItemLiveData = MutableLiveData()
            getById(idItem)
        }

        return theMovieDBItemLiveData as MutableLiveData<TheMovieDBItemDetailsViewModelResponse>
    }

    private fun getById(idItem: Int) {

        dataBaseInstance?.theMovieDBItemDao()?.getById(idItem)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({
                val theMovieDBItemDetailsViewModelResponse =  TheMovieDBItemDetailsViewModelResponse.Builder()
                    .status(StatusResponseDomain.SUCCESSFUL)
                    .theMovieDBItem(it)
                    .build()
                theMovieDBItemLiveData?.postValue(theMovieDBItemDetailsViewModelResponse)

            },{
                val errorMessage = "An error occurred while getting the movies from the database"
                val theMovieDBItemDetailsViewModelResponse =  TheMovieDBItemDetailsViewModelResponse.Builder()
                    .status(StatusResponseDomain.ERROR)
                    .errorMessage(errorMessage)
                    .build()
                theMovieDBItemLiveData?.postValue(theMovieDBItemDetailsViewModelResponse)
            })?.let {
                compositeDisposable.add(it)
            }

    }

}