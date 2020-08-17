package com.wotin.cleansearch.RetrofitServerCheck

import android.util.Log
import android.widget.ImageView
import com.wotin.cleansearch.ApiService.RetrofitClean
import com.wotin.cleansearch.CustomClass.SearchSentencesAnalysisPostCustomClass
import com.wotin.cleansearch.R
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import java.util.logging.Handler
import kotlin.concurrent.thread

class ServerCheckClass {
    fun serverCheck(apiService: RetrofitClean, imageView: ImageView) {
        apiService.requestServerCheck()
            .enqueue(object : retrofit2.Callback<SearchSentencesAnalysisPostCustomClass> {
                override fun onFailure(
                    call: Call<SearchSentencesAnalysisPostCustomClass>,
                    t: Throwable
                ) {
                    imageView.setImageResource(R.drawable.gray_circle)
                    Log.d("TAG", "serverCheckColor is gray in onFailure")
                }

                override fun onResponse(
                    call: Call<SearchSentencesAnalysisPostCustomClass>,
                    response: Response<SearchSentencesAnalysisPostCustomClass>
                ) {
                    if (response.body()!!.server_check) {
                        imageView.setImageResource(R.drawable.orange_circle)
                    } else if (!response.body()!!.server_check) {
                        imageView.setImageResource(R.drawable.green_circle)
                    } else {
                        imageView.setImageResource(R.drawable.gray_circle)
                    }
                }

            })
    }
}