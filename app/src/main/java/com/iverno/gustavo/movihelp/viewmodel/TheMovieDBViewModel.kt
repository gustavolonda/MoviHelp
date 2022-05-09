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
import com.iverno.gustavo.movihelp.repository.TheMovieDBRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class TheMovieDBViewModel : ViewModel(){
    protected val compositeDisposable = CompositeDisposable()
    private var dataBaseInstance: AppDatabase?= null
    private var theMovieDBLiveData : MutableLiveData<TheMovieDBListViewModelResponse>? = null
    private  var typeSearch: String = ""
    private  var textSearch: String = ""
    private  var categorySearch: String = ""
    private  var pageCurrent: Int = 1
    private var isDoewnloading: Boolean = false
    fun setInstanceOfDb(dataBaseInstance: AppDatabase) {
        this.dataBaseInstance = dataBaseInstance
    }
    fun getTheMoviedbLiveData(): LiveData<TheMovieDBListViewModelResponse> {
        if (theMovieDBLiveData == null) {
            theMovieDBLiveData = MutableLiveData()
            getTheMovieItemListByQuery( 0, 0, "", "","")
        }

        return theMovieDBLiveData as MutableLiveData<TheMovieDBListViewModelResponse>
    }
    fun saveTheMoviedbItemList(theMovieDBListViewModelResponse: TheMovieDBListViewModelResponse){

        theMovieDBListViewModelResponse.theMovieDBItemList?.let {
            dataBaseInstance?.theMovieDBItemDao()?.insertTheMoviedbItemList(it)
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe ({
                    getTheMovieItemListByQuery(theMovieDBListViewModelResponse.totalPages,
                                                theMovieDBListViewModelResponse.totalResults,
                                                theMovieDBListViewModelResponse.textSearch,
                                                theMovieDBListViewModelResponse.typeSearch,
                                                theMovieDBListViewModelResponse.categorySearch)
                },{
                    val errorMessage = "An error occurred while saving the movies from the database."
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

    fun getTheMovieItemListByQuery( totalPages: Int,
                                    totalResults: Int,
                                    textSearch: String,
                                    type: String,
                                    category: String){

        val errorMessage = "An error occurred while getting the movies from the database"
        dataBaseInstance?.theMovieDBItemDao()?.getTheMoviedbItem(TheMovieDBRepository.getSearchQuery(textSearch,type,category))
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe ({
                val theMovieDBListViewModelResponse =  TheMovieDBListViewModelResponse.Builder()
                                                                                    .status(SUCCESSFUL)
                                                                                    .theMovieDBItemList(it)
                                                                                    .totalPages(totalPages)
                                                                                    .totalResults(totalResults)
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
    fun saveDownloadedTheMovie(context: Context, page:Int,textSearch: String, type: String, category: String){
        val errorMessage = "An error occurred while connecting to the server"
        Observable.just<TheMovieDBListViewModelResponse>(getTheMovieDBItemList(context, page)).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                it.textSearch = textSearch
                it.typeSearch = type
                it.categorySearch = category
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
    fun getTypeSearch():String{
        return this.typeSearch
    }
    fun setTypeSearch(typeSearch :String){
        this.typeSearch = typeSearch
    }

    fun getTextSearch():String{
        return this.textSearch
    }
    fun setTextSearch(textSearch :String){
        this.textSearch = textSearch
    }
    fun getCategorySearch():String{
        return this.categorySearch
    }
    fun setCategorySearch(categorySearch :String){
        this.categorySearch = categorySearch
    }

    fun getPageCurrent(): Int {
        return  this.pageCurrent
    }
    fun setPageCurrent(pageCurrent : Int ){
        this.pageCurrent = pageCurrent
    }

    fun isDoewnloading(): Boolean {
        return  this.isDoewnloading
    }
    fun setIsDoewnloading(isDoewnloading : Boolean ){
        this.isDoewnloading = isDoewnloading
    }

}