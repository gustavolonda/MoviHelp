package com.iverno.gustavo.movihelp.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.iverno.gustavo.movihelp.bo.ResultResponse
import com.iverno.gustavo.movihelp.data.TheMoviedbItem
import com.iverno.gustavo.movihelp.repository.TheMovieDBRepository.Companion.getTheMovieDBItemList
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable

class TheMovieDBViewModel : ViewModel(){
     var theMovieDBLiveData : MutableLiveData<List<TheMoviedbItem>>? = null
    private lateinit var subscription: Disposable
    fun getTheMoviedbLiveData(context: Context): LiveData<List<TheMoviedbItem>> {
        if (theMovieDBLiveData == null) {
            theMovieDBLiveData = MutableLiveData()
            initPanel(context, 1)
        }

        return theMovieDBLiveData as MutableLiveData<List<TheMoviedbItem>>
    }
    fun initPanel(context: Context, page:Int){
        subscription = getTheMovieList(context, page).subscribe (
            { s -> theMovieDBLiveData?.postValue(s.theMovieDBItemList)
            },
            { e -> println(e) },
            {      println("onComplete") })
    }

    fun getTheMovieList(context: Context, page:Int): Observable<TheMovieDBListViewModelResponse> {
        return Observable.create {
                subscriber ->
                            // api call...
                            subscriber.onNext(getTheMovieDBItemList(context, page))
                            subscriber.onComplete()
        }
    }
}