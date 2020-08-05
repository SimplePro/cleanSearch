package com.simplepro.cleansearch.ApiService

import com.simplepro.cleansearch.Instance.SearchSentencesAnalysisInstance
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
    ) : Call<SearchSentencesAnalysisInstance>

    @GET("clean_get/id={pk}/")
    fun requestGET(
        @Path("pk") id : String) : Call<SearchSentencesAnalysisInstance>



}