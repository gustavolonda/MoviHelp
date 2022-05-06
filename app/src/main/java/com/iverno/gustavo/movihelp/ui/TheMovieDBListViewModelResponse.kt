package com.iverno.gustavo.movihelp.ui

import com.google.gson.annotations.SerializedName
import com.iverno.gustavo.movihelp.data.TheMoviedbItem

class TheMovieDBListViewModelResponse private constructor(builder: TheMovieDBListViewModelResponse.Builder){
    val status : String?
    val errorMessage : String?
    var message : String?
    val theMovieDBItemList : List<TheMoviedbItem>?
    val currentPage: Int?
    val totalPages: Int?
    val totalResults: Int?
    init {
        this.status             = builder.status
        this.errorMessage       = builder.errorMessage
        this.message            = builder.message
        this.theMovieDBItemList = builder.theMovieDBItemList
        this.currentPage        = builder.currentPage
        this.totalPages         = builder.totalPages
        this.totalResults       = builder.totalResults

    }

    class Builder{
        var status : String? = null
            private set
        var errorMessage: String? = null
            private set
        var message: String? = null
            private set
        var theMovieDBItemList: List<TheMoviedbItem>? = null
            private set
        var currentPage: Int? = null
            private set
        var totalPages: Int? = null
            private set
        var totalResults: Int? = null
            private set

        fun status(status: String?)              = apply { this.status = status }
        fun errorMessage(errorMessage: String?)  = apply { this.errorMessage = errorMessage }
        fun message(errorMessage: String?)       = apply { this.message = message }
        fun theMovieDBItemList(theMovieDBItemList:  List<TheMoviedbItem>?) = apply { this.theMovieDBItemList = theMovieDBItemList }
        fun currentPage(currentPage: Int?)       = apply { this.currentPage = currentPage }
        fun totalPages(totalPages: Int?)         = apply { this.totalPages = totalPages }
        fun totalResults(totalResults: Int?)     = apply { this.totalResults = totalResults }
        fun build() = TheMovieDBListViewModelResponse(this)
    }
}