package com.iverno.gustavo.movihelp.data

class TheMovieDBItemDetailsViewModelResponse private constructor(builder: Builder){
    var status : String?
    val errorMessage : String?
    var message : String?
    val theMovieDBItem : TheMoviedbItem?

    init {
        this.status             = builder.status
        this.errorMessage       = builder.errorMessage
        this.message            = builder.message
        this.theMovieDBItem     = builder.theMovieDBItem


    }

    class Builder{
        var status : String? = null
            private set
        var errorMessage: String? = null
            private set
        var message: String? = null
            private set
        var theMovieDBItem: TheMoviedbItem? = null
            private set

        fun status(status: String?)              = apply { this.status = status }
        fun errorMessage(errorMessage: String?)  = apply { this.errorMessage = errorMessage }
        fun message(message: String?)            = apply { this.message = message }
        fun theMovieDBItem(theMovieDBItem: TheMoviedbItem?) = apply { this.theMovieDBItem = theMovieDBItem }
       fun build() = TheMovieDBItemDetailsViewModelResponse(this)
    }
}