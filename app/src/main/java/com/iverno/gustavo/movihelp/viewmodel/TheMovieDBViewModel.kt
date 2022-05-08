package com.iverno.gustavo.movihelp.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iverno.gustavo.movihelp.bo.StatusResponseDomain.SUCCESSFUL
import com.iverno.gustavo.movihelp.bo.StatusResponseDomain.ERROR
import com.iverno.gustavo.movihelp.db.AppDatabase
import com.iverno.gustavo.movihelp.repository.TheMovieDBRepository.Companion.getTheMovieDBItemList
import com.iverno.gustavo.movihelp.data.TheMovieDBListViewModelResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class TheMovieDBViewModel : ViewModel(){
    protected val compositeDisposable = CompositeDisposable()
    private var dataBaseInstance: AppDatabase?= null
    private var theMovieDBLiveData : MutableLiveData<TheMovieDBListViewModelResponse>? = null
    private lateinit var subscription: Disposable

    fun setInstanceOfDb(dataBaseInstance: AppDatabase) {
        this.dataBaseInstance = dataBaseInstance
    }
    fun getTheMoviedbLiveData(): LiveData<TheMovieDBListViewModelResponse> {
        if (theMovieDBLiveData == null) {
            theMovieDBLiveData = MutableLiveData()
            getTheMovieItemListFromDataBase()
        }

        return theMovieDBLiveData as MutableLiveData<TheMovieDBListViewModelResponse>
    }
    fun saveTheMoviedbItemList(theMovieDBListViewModelResponse: TheMovieDBListViewModelResponse){
        val errorMessage = "An error occurred while saving the movies from the database."
        theMovieDBListViewModelResponse.theMovieDBItemList?.let {
            dataBaseInstance?.theMovieDBItemDao()?.insertTheMoviedbItemList(it)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe ({

                    theMovieDBListViewModelResponse.message = "The data was saved successfully"
                    theMovieDBLiveData?.postValue(theMovieDBListViewModelResponse)
                },{
                    val theMovieDBListViewModelResponse =  TheMovieDBListViewModelResponse.Builder()
                        .status(ERROR)
                        .errorMessage(errorMessage)
                        .build()
                    theMovieDBLiveData?.postValue(theMovieDBListViewModelResponse)
                })?.let {
                    compositeDisposable.add(it)
                }
        }
    }

    fun getTheMovieItemListFromDataBase(){
        val errorMessage = "An error occurred while getting the movies from the database"
        dataBaseInstance?.theMovieDBItemDao()?.getAllRecords()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({
                val theMovieDBListViewModelResponse =  TheMovieDBListViewModelResponse.Builder()
                                                                                    .status(SUCCESSFUL)
                                                                                    .theMovieDBItemList(it)
                                                                                    .build()
                theMovieDBLiveData?.postValue(theMovieDBListViewModelResponse)

            },{
                val theMovieDBListViewModelResponse =  TheMovieDBListViewModelResponse.Builder()
                                                                                    .status(ERROR)
                                                                                    .errorMessage(errorMessage)
                                                                                    .build()
                theMovieDBLiveData?.postValue(theMovieDBListViewModelResponse)
            })?.let {
                compositeDisposable.add(it)
            }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        compositeDisposable.clear()
        super.onCleared()
    }
    fun saveDownloadedTheMovie(context: Context, page:Int){
        val errorMessage = "An error occurred while connecting to the server"
        Observable.just<TheMovieDBListViewModelResponse>(getTheMovieDBItemList(context, page)).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                saveTheMoviedbItemList(it)
            },{

                val theMovieDBListViewModelResponse =  TheMovieDBListViewModelResponse.Builder()
                    .status(ERROR)
                    .errorMessage(errorMessage)
                    .build()
                theMovieDBLiveData?.postValue(theMovieDBListViewModelResponse)
            })?.let {
                compositeDisposable.add(it)
            }
    }

}