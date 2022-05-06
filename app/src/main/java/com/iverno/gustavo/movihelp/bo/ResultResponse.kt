package com.iverno.gustavo.movihelp.bo

import com.google.gson.annotations.SerializedName
import com.iverno.gustavo.movihelp.data.TheMoviedbItem


class ResultResponse  private constructor(
                                        @SerializedName("results")       val theMovieDBItemList:List<TheMoviedbItem>?,
                                        @SerializedName("total_pages")   val totalPages: Int,
                                        @SerializedName("total_results") val totalResults: Int)
