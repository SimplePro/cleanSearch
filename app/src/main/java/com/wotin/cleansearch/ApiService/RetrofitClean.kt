package com.wotin.cleansearch.ApiService

import com.wotin.cleansearch.CustomClass.SearchSentencesAnalysisGetCustomClass
import com.wotin.cleansearch.CustomClass.SearchSentencesAnalysisPostCustomClass
import retrofit2.Call
import retrofit2.http.*

interface RetrofitClean {

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("clean_post/browser={browser}/")
    fun requestPOST(
        @Field("sentence") sentence: String,
        @Field("id") id: String,
        @Path("browser") browser: String
    ): Call<SearchSentencesAnalysisPostCustomClass>

    @GET("clean_get/id={pk}/")
    fun requestGET(
        @Path("pk") pk: String
    ): Call<SearchSentencesAnalysisGetCustomClass>

    @POST("clean_server_check/")
    fun requestServerCheck(
    ): Call<SearchSentencesAnalysisPostCustomClass>


}