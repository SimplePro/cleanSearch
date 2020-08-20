package com.wotin.cleansearch.Activity

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonObject
import com.wotin.cleansearch.Adapter.AddAndReplaceSynonymRecyclerViewAdapter
import com.wotin.cleansearch.ApiService.RetrofitClean
import com.wotin.cleansearch.Converters.ListJsonConverterString
import com.wotin.cleansearch.Extensions.onEditTextChanged
import com.wotin.cleansearch.R
import kotlinx.android.synthetic.main.activity_add_synonym.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type

class AddAndReplaceSynonymActivity : AppCompatActivity(), AddAndReplaceSynonymRecyclerViewAdapter.LongClickListener {

    var synonymArrayList : ArrayList<String> = arrayListOf()
    lateinit var synonymAdapter : AddAndReplaceSynonymRecyclerViewAdapter

    val baseUrl = "http://172.23.16.1:8080"
    lateinit var retrofit : Retrofit
    lateinit var apiService : RetrofitClean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_synonym)

        synonymAdapter = AddAndReplaceSynonymRecyclerViewAdapter(synonymArrayList, this)

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(RetrofitClean::class.java)

        settingRecyclerView()

        synonymWordEditText.onEditTextChanged {s ->
            if(s.toString().isNotEmpty()) {
                synonymWordTextView.text = "'${s.toString()}'"
                synonymLottieAnimationBottomSynonymTextView.text = "'${s.toString()}'"
            }
            else if(s.toString().isEmpty()) {
                synonymWordTextView.text = "'???'"
                synonymLottieAnimationBottomSynonymTextView.text = "'???'"
            }
        }

        addSynonymButton.setOnClickListener {
            if(synonymWordEditText.text.isEmpty()) Toast.makeText(applicationContext, "어떤 단어에 대한 동의어인지 입력해주세요", Toast.LENGTH_LONG).show()
            else if(addSynonymEditText.text.isEmpty()) Toast.makeText(applicationContext, "동의어를 입력해주세요", Toast.LENGTH_LONG).show()
            else {
                synonymArrayList.add(addSynonymEditText.text.toString())
                addSynonymEditText.text = null
                synonymAdapter.notifyDataSetChanged()
            }
            controlLottieAnimationVisible()
        }

        LeftImageViewAddSynonymActivity.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        addSynonymConfirmButton.setOnClickListener {
            synonymArrayList.add(0, synonymWordEditText.text.toString())
            val dataList = ListJsonConverterString().listToJson(synonymArrayList)
            Log.d("TAG", "dataList is $dataList")
            apiService.requestCleanSaveSynonymNouns(dataList).enqueue(object : retrofit2.Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    Toast.makeText(applicationContext, "소중한 단어 감사합니다!", Toast.LENGTH_LONG).show()
                    Log.d("TAG", "성공")
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    Toast.makeText(applicationContext, "서버가 꺼져있습니다.", Toast.LENGTH_LONG).show()
                    Log.d("TAG", "실패 $t")
                }

            })
        }
    }

    private fun controlLottieAnimationVisible() {
        if (synonymArrayList.isNotEmpty()) synonymLottieAnimationLayout.visibility = View.GONE
        else synonymLottieAnimationLayout.visibility = View.VISIBLE
    }

    private fun settingRecyclerView(){
        synonymRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@AddAndReplaceSynonymActivity, LinearLayoutManager.VERTICAL, false)
            adapter = synonymAdapter
            setHasFixedSize(true)
        }
    }


    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun longClick(position: Int) {
        val addAndReplaceSynonymDialog = AlertDialog.Builder(this)
        val addAndReplaceSynonymEdialog = LayoutInflater.from(this)
        val addAndReplaceSynonymMView = addAndReplaceSynonymEdialog.inflate(R.layout.key_word_item_remove_dialog, null)
        val addAndReplaceSynonymBuilder = addAndReplaceSynonymDialog.create()

        val addAndReplaceSynonymRemoveButtonDialog =
            addAndReplaceSynonymMView.findViewById<Button>(R.id.keyWordRemoveButtonDialog)
        val addAndReplaceSynonymCancelButtonDialog =
            addAndReplaceSynonymMView.findViewById<Button>(R.id.keyWordRemoveCancelButtonDialog)

        val addAndReplaceSynonymTextViewDialog = addAndReplaceSynonymMView.findViewById<TextView>(R.id.keyWordTextViewDialog)
        addAndReplaceSynonymTextViewDialog.text = "동의어를 지우시겠습니까?"

        addAndReplaceSynonymBuilder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        addAndReplaceSynonymBuilder.window?.requestFeature(Window.FEATURE_NO_TITLE)

        addAndReplaceSynonymBuilder.setView(addAndReplaceSynonymMView)
        addAndReplaceSynonymBuilder.show()

        addAndReplaceSynonymRemoveButtonDialog.setOnClickListener {
            synonymArrayList.removeAt(position)
            if (synonymArrayList.size == 0) {
                val animation = AnimationUtils.loadAnimation(
                    this,
                    R.anim.lottie_animation_alpha_visible_animation
                )
            }
            synonymAdapter.notifyDataSetChanged()
            controlLottieAnimationVisible()
            addAndReplaceSynonymBuilder.dismiss()
        }

        addAndReplaceSynonymCancelButtonDialog.setOnClickListener {
            addAndReplaceSynonymBuilder.dismiss()
        }
    }

}