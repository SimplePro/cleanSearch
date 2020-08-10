package com.wotin.cleansearch.ApiService

import com.wotin.cleansearch.CustomClass.SearchSentencesAnalysisGetCustomClass
import com.wotin.cleansearch.CustomClass.SearchSentencesAnalysisCustomClass
import retrofit2.Call
import retrofit2.http.*

interface RetrofitClean {

    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("clean_post/browser={browser}/")
    fun requestPOST(
        @Field("sentence") sentence : String,
        @Field("id") id : String,
        @Path("browser") browser : String
    ) : Call<SearchSentencesAnalysisCustomClass>

    @GET("clean_get/id={pk}/")
    fun requestGET(
        @Path("pk") id : String) : Call<SearchSentencesAnalysisGetCustomClass>



}