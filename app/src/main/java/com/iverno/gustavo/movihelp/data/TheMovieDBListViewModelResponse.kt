package com.iverno.gustavo.movihelp.data

class TheMovieDBListViewModelResponse private constructor(builder: Builder){
    var status : String?
    val errorMessage : String?
    var message : String?
    val theMovieDBItemList : List<TheMoviedbItem>?
    val currentPage: Int?
    var totalPages: Int = 0
    var totalResults: Int = 0
    var typeSearch: String = ""
    var textSearch: String = ""
    var categorySearch: String = ""
    var isDoewnloading: Boolean = false
    init {
        this.status             = builder.status
        this.errorMessage       = builder.errorMessage
        this.message            = builder.message
        this.theMovieDBItemList = builder.theMovieDBItemList
        this.currentPage        = builder.currentPage
        this.totalPages         = builder.totalPages
        this.totalResults       = builder.totalResults
        this.typeSearch         = builder.typeSearch
        this.textSearch         = builder.textSearch
        this.categorySearch     = builder.categorySearch
        this.isDoewnloading     = builder.isDoewnloading

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
        var totalPages: Int = 0
            private set
        var totalResults: Int = 0
            private set
        var typeSearch: String = ""
            private set
        var textSearch: String = ""
            private set
        var categorySearch: String = ""
            private set
        var isDoewnloading: Boolean = false
            private set

        fun status(status: String?)              = apply { this.status = status }
        fun errorMessage(errorMessage: String?)  = apply { this.errorMessage = errorMessage }
        fun message(message: String?)            = apply { this.message = message }
        fun theMovieDBItemList(theMovieDBItemList:  List<TheMoviedbItem>?) = apply { this.theMovieDBItemList = theMovieDBItemList }
        fun currentPage(currentPage: Int?)       = apply { this.currentPage = currentPage }
        fun totalPages(totalPages: Int)         = apply { this.totalPages = totalPages }
        fun totalResults(totalResults: Int)     = apply { this.totalResults = totalResults }
        fun typeSearch(typeSearch: String?)      = apply {
            if (typeSearch != null) {
                this.typeSearch = typeSearch
            }
        }
        fun textSearch(textSearch: String?)       = apply {
            if (textSearch != null) {
                this.textSearch = textSearch
            }
        }
        fun categorySearch(categorySearch: String?)       = apply {
            if (categorySearch != null) {
                this.categorySearch = categorySearch
            }
        }
        fun isDoewnloading(isDoewnloading: Boolean)     = apply { this.isDoewnloading = isDoewnloading }
        fun build() = TheMovieDBListViewModelResponse(this)
    }
}