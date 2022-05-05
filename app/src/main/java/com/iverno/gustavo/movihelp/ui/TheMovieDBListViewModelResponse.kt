package com.iverno.gustavo.movihelp.ui

import com.iverno.gustavo.movihelp.data.TheMoviedbItem

class TheMovieDBListViewModelResponse private constructor(builder: TheMovieDBListViewModelResponse.Builder){
    val status : String?
    val errorMessage : String?
    val theMovieDBItemList : List<TheMoviedbItem>?
    init {
        this.status             = builder.status
        this.errorMessage       = builder.errorMessage
        this.theMovieDBItemList = builder.theMovieDBItemList
    }

    class Builder{
        var status : String? = null
            private set
        var errorMessage: String? = null
            private set
        var theMovieDBItemList: List<TheMoviedbItem>? = null
            private set

        fun status(status: String?) = apply { this.status = status }
        fun errorMessage(errorMessage: String?) = apply { this.errorMessage = errorMessage }
        fun theMovieDBItemList(theMovieDBItemList:  List<TheMoviedbItem>?) = apply { this.theMovieDBItemList = theMovieDBItemList }
        fun build() = TheMovieDBListViewModelResponse(this)
    }
}