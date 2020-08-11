package com.wotin.cleansearch.activity

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
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.wotin.cleansearch.ApiService.RetrofitClean
import com.wotin.cleansearch.Converters.MapJsonConverter
import com.wotin.cleansearch.CustomClass.SearchResultCustomClass
import com.wotin.cleansearch.CustomClass.SearchResultsRecordCustomClass
import com.wotin.cleansearch.CustomClass.SearchSentencesAnalysisGetCustomClass
import com.wotin.cleansearch.CustomClass.SearchSentencesAnalysisCustomClass
import com.wotin.cleansearch.DB.SearchResultRecordsDB
import com.wotin.cleansearch.R
import com.wotin.cleansearch.adapter.FieldWordRecyclerViewAdapter
import com.wotin.cleansearch.adapter.KeyWordRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity(),
    KeyWordRecyclerViewAdapter.ItemViewSetOnLongClickListener {

    //변수 선언
    var spinnerList: ArrayList<String> =
        arrayListOf("선택", "개발", "과학", "역사", "수학", "공부", "진로", "건강", "운동")
    var spinnerAdapter: ArrayAdapter<String>? = null
    var selectField: String = "선택"
    var fieldWordList: ArrayList<String> = arrayListOf()
    var fieldWordAdapter: FieldWordRecyclerViewAdapter = FieldWordRecyclerViewAdapter(fieldWordList)
    var keyWordList: ArrayList<String> = arrayListOf()
    var keyWordAdapter: KeyWordRecyclerViewAdapter? = null
    var selectBrowserText: String = "NAVER"

    //서버에서 만든 문장, 각각의 크롤링 결과를 담는 변수
    lateinit var cleanSearchResultMap : Map<String, Any>
    var cleanResultList : ArrayList<SearchResultCustomClass> = arrayListOf()

    var keyWordLottieAnimationBool = false

    lateinit var retrofit: Retrofit
    lateinit var apiService: RetrofitClean

    //UUID 값인데 보낸 UUID 값 저장하는 변수임. 서버에서 데이터 가져올때 저장된 UUID 값으로 다시 가져오기 위해서.
    lateinit var retrofitId: String

    //explain 다이얼로그 변수임.
    lateinit var KeyWordDialog: AlertDialog.Builder
    lateinit var KeyWordEdialog: LayoutInflater

    lateinit var KeyWordMView: View

    lateinit var KeyWordBuilder: AlertDialog

    lateinit var explainDialog: AlertDialog.Builder
    lateinit var explainEdialog: LayoutInflater
    lateinit var explainMView: View
    lateinit var explainBuilder: AlertDialog

    lateinit var explainCleanSearchEditTextTitle: ConstraintLayout
    lateinit var explainCleanSearchEditTextContent: LinearLayout
    lateinit var explainCleanSearchEditTextArrow: ImageView

    lateinit var explainCleanSearchFieldTitle: ConstraintLayout
    lateinit var explainCleanSearchFieldContent: LinearLayout
    lateinit var explainCleanSearchFieldArrow: ImageView

    lateinit var explainCleanSearchKeyWordTitle: ConstraintLayout
    lateinit var explainCleanSearchKeyWordContent: LinearLayout
    lateinit var explainCleanSearchKeyWordArrow: ImageView

    lateinit var explainCleanSearchBrowserTitle: ConstraintLayout
    lateinit var explainCleanSearchBrowserContent: LinearLayout
    lateinit var explainCleanSearchBrowserArrow: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //변수 정의
        val fieldRecyclerViewLayoutManager = GridLayoutManager(applicationContext, 2)
        val keyWordRecyclerViewLayoutManager = GridLayoutManager(applicationContext, 2)
        val API_URL = "http://220.72.174.114:8080"
        retrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apiService = retrofit.create(RetrofitClean::class.java)

        //분야에 맞는 단어들을 정의하는 부분.
        val fieldWordMap = mapOf<String, ArrayList<String>>(
            "선택" to arrayListOf(),
            "개발" to arrayListOf("stackOverFlow", "StackOverFlow", "개발자", "개발"),
            "과학" to arrayListOf("돌", "화산", "지진", "화석", "화성", "우주", "지구", "땅", "물", "불", "흙", "공기"),
            "역사" to arrayListOf("지리", "역사"),
            "수학" to arrayListOf("더하기", "나누기", "빼기", "곱하기"),
            "공부" to arrayListOf("과목", "학교"),
            "진로" to arrayListOf("꿈", "대학교", "취직", "취업"),
            "건강" to arrayListOf("건강", "양파"),
            "운동" to arrayListOf("축구", "농구", "야구", "배구", "배드민턴", "탁구")
        )

        //아답터 연결.
        keyWordAdapter = KeyWordRecyclerViewAdapter(keyWordList, this)
        spinnerAdapter = ArrayAdapter(
            this,
            R.layout.item_field_spinner, spinnerList
        )

        //시작했을 때 윈도우 조정
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        //3초 마다 윈도우 조정해주는 메소드.
        controlWindowOnTimer()

        //앱 사용방법 이미지뷰가 클릭 되었을 때 다이얼로그를 띄어주는 메소드를 실행한다.
        explainApplicationImageView.setOnClickListener {
            showExplainApplicationDialog()
        }

        //검색기록 이미지뷰가 클릭되었을 때
        searchRecordImageView.setOnClickListener {
            val intent = Intent(this, SearchResultsRecordActivity::class.java)
            startActivity(intent)
            finish()
        }

        //스피너 관련 코드
        fieldSpinner.adapter = spinnerAdapter
        fieldSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                if (position != 0) {
                    if (selectFieldLottieAnimationView.visibility == View.VISIBLE) {
                        val animation = AnimationUtils.loadAnimation(
                            this@MainActivity,
                            R.anim.lottie_animation_alpha_gone_animation
                        )
                        selectFieldLottieAnimationView.startAnimation(animation)
                        Handler().postDelayed({
                            selectFieldLottieAnimationView.visibility = View.GONE
                        }, 500)
                    }
                } else if (position == 0) {
                    val animation = AnimationUtils.loadAnimation(
                        this@MainActivity,
                        R.anim.lottie_animation_alpha_visible_animation
                    )
                    selectFieldLottieAnimationView.visibility = View.VISIBLE
                    selectFieldLottieAnimationView.startAnimation(animation)
                }
                selectField = spinnerList[position]
                Log.d("TAG", "selectField is $selectField")
                fieldWordList = fieldWordMap[selectField]!!
                Log.d("TAG", "fieldWordList is $fieldWordList")
                fieldWordAdapter = FieldWordRecyclerViewAdapter(fieldWordList)
                fieldWordAdapter.notifyDataSetChanged()
                fieldWordRecyclerView.adapter = fieldWordAdapter
            }
        }

        //field RecyclerView
        fieldWordRecyclerView.apply {
            adapter = fieldWordAdapter
            layoutManager = fieldRecyclerViewLayoutManager
            setHasFixedSize(true)
        }

        //keyWord RecyclerView
        keywordRecyclerView.apply {
            adapter = keyWordAdapter
            layoutManager = keyWordRecyclerViewLayoutManager
            setHasFixedSize(true)
        }

        //addKeyWordButton
        addKeyWordButton.setOnClickListener {
            if (KeyWordEditText.text.isNotEmpty()) {
                keyWordList.add(KeyWordEditText.text.toString())
                keyWordAdapter!!.notifyDataSetChanged()
                KeyWordEditText.text = null
                if (keyWordList.size == 1) {
                    keyWordLottieAnimationBool = true
                }
                if (keyWordLottieAnimationBool == true) {
                    val animation = AnimationUtils.loadAnimation(
                        this,
                        R.anim.lottie_animation_alpha_gone_animation
                    )
                    noKeyWordLottieAnimation.startAnimation(animation)
                    Handler().postDelayed({
                        noKeyWordLottieAnimation.visibility = View.GONE
                    }, 500)
                    keyWordLottieAnimationBool = false
                }
            } else if (KeyWordEditText.text.isEmpty()) {
                Toast.makeText(applicationContext, "키워드를 입력해주세요", Toast.LENGTH_LONG).show()
            }
        }

        cleanButton.setOnClickListener {
            if(selectField != "선택"){
                retrofitId = UUID.randomUUID().toString().replace("-", "")
                val sentence = cleanSearchEditText.text.toString()
                retrofitPOST(sentence, retrofitId)
                Handler().postDelayed({
                    retrofitGET(retrofitId)
                }, 3000)
            } else {
                Toast.makeText(applicationContext, "검색 분야를 선택해주세요.", Toast.LENGTH_LONG).show()
            }
        }

        //naverBrowserClicked
        naverBrowserLayout.setOnClickListener {
            naverBrowserClicked()
        }

        //googleBrowserClicked
        googleBrowserLayout.setOnClickListener {
            googleBrowserClicked()
        }

        //daumBrowserClicked
        daumBrowserLayout.setOnClickListener {
            daumBrowserClicked()
        }
    }

    //앱 사용방법 다이얼로그를 띄어주는 메소드
    private fun showExplainApplicationDialog() {
        explainDialog = AlertDialog.Builder(this)
        explainEdialog = LayoutInflater.from(this)
        explainMView = explainEdialog.inflate(R.layout.explain_application_dialog, null)
        explainBuilder = explainDialog.create()

        explainCleanSearchEditTextTitle =
            explainMView.findViewById<ConstraintLayout>(R.id.explainCleanSearchEditTextDialogTitle)
        explainCleanSearchEditTextContent =
            explainMView.findViewById<LinearLayout>(R.id.explainCleanSearchEditTextDialogContent)
        explainCleanSearchEditTextArrow =
            explainMView.findViewById<ImageView>(R.id.expandableArrowExplainCleanSearchEditText)

        explainCleanSearchFieldTitle =
            explainMView.findViewById<ConstraintLayout>(R.id.explainSearchFieldDialogTitle)
        explainCleanSearchFieldContent =
            explainMView.findViewById<LinearLayout>(R.id.explainSearchFieldDialogContent)
        explainCleanSearchFieldArrow =
            explainMView.findViewById<ImageView>(R.id.expandableArrowExplainSearchField)

        explainCleanSearchKeyWordTitle =
            explainMView.findViewById<ConstraintLayout>(R.id.explainSearchKeyWordDialogTitle)
        explainCleanSearchKeyWordContent =
            explainMView.findViewById<LinearLayout>(R.id.explainSearchKeyWordDialogContent)
        explainCleanSearchKeyWordArrow =
            explainMView.findViewById<ImageView>(R.id.expandableArrowExplainSearchKeyWord)

        explainCleanSearchBrowserTitle =
            explainMView.findViewById<ConstraintLayout>(R.id.explainSearchBrowserDialogTitle)
        explainCleanSearchBrowserContent =
            explainMView.findViewById<LinearLayout>(R.id.explainSearchBrowserDialogContent)
        explainCleanSearchBrowserArrow =
            explainMView.findViewById<ImageView>(R.id.expandableArrowExplainSearchBrowser)



        explainBuilder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        explainBuilder.window?.requestFeature(Window.FEATURE_NO_TITLE)

        explainBuilder.setView(explainMView)
        explainBuilder.show()

        explainCleanSearchEditTextTitle.setOnClickListener {
            if (explainCleanSearchEditTextContent.visibility == View.VISIBLE) {
                explainCleanSearchEditTextContent.visibility = View.GONE
                explainCleanSearchEditTextArrow.setImageResource(R.drawable.bottom_arrow)
            } else {
                explainCleanSearchEditTextContent.visibility = View.VISIBLE
                explainCleanSearchEditTextArrow.setImageResource(R.drawable.top_arrow)
            }
        }

        explainCleanSearchFieldTitle.setOnClickListener {
            if (explainCleanSearchFieldContent.visibility == View.VISIBLE) {
                explainCleanSearchFieldContent.visibility = View.GONE
                explainCleanSearchFieldArrow.setImageResource(R.drawable.bottom_arrow)
            } else {
                explainCleanSearchFieldContent.visibility = View.VISIBLE
                explainCleanSearchFieldArrow.setImageResource(R.drawable.top_arrow)
            }
        }

        explainCleanSearchKeyWordTitle.setOnClickListener {
            if (explainCleanSearchKeyWordContent.visibility == View.VISIBLE) {
                explainCleanSearchKeyWordContent.visibility = View.GONE
                explainCleanSearchKeyWordArrow.setImageResource(R.drawable.bottom_arrow)
            } else {
                explainCleanSearchKeyWordContent.visibility = View.VISIBLE
                explainCleanSearchKeyWordArrow.setImageResource(R.drawable.top_arrow)
            }
        }

        explainCleanSearchBrowserTitle.setOnClickListener {
            if (explainCleanSearchBrowserContent.visibility == View.VISIBLE) {
                explainCleanSearchBrowserContent.visibility = View.GONE
                explainCleanSearchBrowserArrow.setImageResource(R.drawable.bottom_arrow)
            } else {
                explainCleanSearchBrowserContent.visibility = View.VISIBLE
                explainCleanSearchBrowserArrow.setImageResource(R.drawable.top_arrow)
            }
        }

    }

    //keyWord 가 롱클릭 됬을 때.
    override fun itemViewLongClick(position: Int) {
        KeyWordDialog = AlertDialog.Builder(this)
        KeyWordEdialog = LayoutInflater.from(this)
        KeyWordMView = KeyWordEdialog.inflate(R.layout.key_word_item_remove_dialog, null)
        KeyWordBuilder = KeyWordDialog.create()

        val KeyWordRemoveButtonDialog =
            KeyWordMView.findViewById<Button>(R.id.keyWordRemoveButtonDialog)
        val KeyWordCancelButtonDialog =
            KeyWordMView.findViewById<Button>(R.id.keyWordRemoveCancelButtonDialog)

        KeyWordBuilder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        KeyWordBuilder.window?.requestFeature(Window.FEATURE_NO_TITLE)

        KeyWordBuilder.setView(KeyWordMView)
        KeyWordBuilder.show()

        KeyWordRemoveButtonDialog.setOnClickListener {
            keyWordList.removeAt(position)
            if (keyWordList.size == 0) {
                val animation = AnimationUtils.loadAnimation(
                    this,
                    R.anim.lottie_animation_alpha_visible_animation
                )
                noKeyWordLottieAnimation.visibility = View.VISIBLE
                noKeyWordLottieAnimation.startAnimation(animation)
            }
            keyWordAdapter!!.notifyDataSetChanged()
            KeyWordBuilder.dismiss()
        }

        KeyWordCancelButtonDialog.setOnClickListener {
            KeyWordBuilder.dismiss()
        }

    }

    //naverBrowser 가 클릭되었을 때 실행되는 메소드.
    private fun naverBrowserClicked() {
        selectBrowserText = "NAVER"
        selectBrowserTextView.setText("검색 브라우저* (현재 브라우저 : $selectBrowserText)")
        naverBrowserLayout.setBackgroundResource(R.drawable.select_browser_background)
        googleBrowserLayout.setBackgroundResource(R.drawable.no_select_browser_background)
        daumBrowserLayout.setBackgroundResource(R.drawable.no_select_browser_background)
    }

    //googleBrowser 가 클릭되었을 때 실행되는 메소드.
    private fun googleBrowserClicked() {
        selectBrowserText = "GOOGLE"
        selectBrowserTextView.setText("검색 브라우저* (현재 브라우저 : $selectBrowserText)")
        googleBrowserLayout.setBackgroundResource(R.drawable.select_browser_background)
        naverBrowserLayout.setBackgroundResource(R.drawable.no_select_browser_background)
        daumBrowserLayout.setBackgroundResource(R.drawable.no_select_browser_background)
    }

    //daumBrowser 가 클릭되었을 때 실행되는 메소드.
    private fun daumBrowserClicked() {
        selectBrowserText = "DAUM"
        selectBrowserTextView.setText("검색 브라우저* (현재 브라우저 : $selectBrowserText)")
        daumBrowserLayout.setBackgroundResource(R.drawable.select_browser_background)
        naverBrowserLayout.setBackgroundResource(R.drawable.no_select_browser_background)
        googleBrowserLayout.setBackgroundResource(R.drawable.no_select_browser_background)
    }

    //3초마다 윈도우를 조정해주는 메소드.
    private fun controlWindowOnTimer() {
        timer(period = 3000)
        {
            runOnUiThread {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE or
                        View.SYSTEM_UI_FLAG_FULLSCREEN or
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            }
        }
    }

    //서버에 값 보낼 때 실행하는 메소드.
    private fun retrofitPOST(sentence: String, id: String) {
        apiService.requestPOST(sentence = sentence, id = id, browser = selectBrowserText)
            .enqueue(object : retrofit2.Callback<SearchSentencesAnalysisCustomClass> {
                override fun onResponse(
                    call: Call<SearchSentencesAnalysisCustomClass>,
                    response: Response<SearchSentencesAnalysisCustomClass>
                ) {
                    try {
                        if(response.body()!!.sentence == "server is Checking") {
                            //서버 점검 시간일 때 작동하는 코드.
                            Toast.makeText(applicationContext, "${response.body()!!.from_time} ~ ${response.body()!!.to_time} 서버 점검 시간입니다.", Toast.LENGTH_LONG).show()
                        }
                        else {
                            Toast.makeText(
                                applicationContext,
                                response.body()!!.sentence,
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } catch (e: Exception) {
                        Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
                        Log.d("TAG", "error is ${e.message} in post onResponse")
                    }
                }

                override fun onFailure(
                    call: Call<SearchSentencesAnalysisCustomClass>,
                    t: Throwable
                ) {
                    Log.d("TAG", t.message)
                }

            })
    }

    //서버에서 값 가져올 때 실행하는 메소드.
    private fun retrofitGET(id: String) {
        apiService.requestGET(id).enqueue(object : Callback<SearchSentencesAnalysisGetCustomClass> {
            override fun onFailure(
                call: Call<SearchSentencesAnalysisGetCustomClass>,
                t: Throwable
            ) {
                Log.d("TAG", "error is $t in get")
            }

            override fun onResponse(
                call: Call<SearchSentencesAnalysisGetCustomClass>,
                response: Response<SearchSentencesAnalysisGetCustomClass>
            ) {
                try {
                    cleanSearchResultMap = MapJsonConverter().MapToJsonConverter(response.body()?.result.toString())
                    Log.d("TAG", "cleanSearchResultMap is $cleanSearchResultMap")
                    analysisData()
                } catch (e: Exception) {
                    Log.d("TAG", "error is $e in get onResponse")
                }
            }

        })
    }

    private fun analysisData(){
        cleanResultList = arrayListOf()
        for((sentence, crawling) in cleanSearchResultMap){
            var data : SearchResultCustomClass
            var score = 0
            var fieldCount = 0
            var keyWordCount = 0
            Log.d("TAG", "analysisData function is first for")
            val crawlingList : List<String> = crawling.toString().split(" ", "은", "의", "는", "입니다", "를", "을").map { it.trim() }
            for(i in fieldWordList)
            {
                fieldCount += crawlingList.count {string -> string == i}
                Log.d("TAG", "fieldCount is $fieldCount")
            }
            for(i in keyWordList)
            {
                keyWordCount += crawlingList.count { string -> string == i }
                Log.d("TAG", "keyWordCount is $keyWordCount")
            }
            score += fieldCount * 5
            score += keyWordCount * 50
            data = SearchResultCustomClass(sentence, 0, score)
            cleanResultList.add(data)
        }
        cleanResultList.sortBy { it -> it.score }
        for(i in 0 .. cleanResultList.size - 1)
        {
            cleanResultList[i].rank = i + 1
            Log.d("TAG", "cleanResultList[$i] sentence is ${cleanResultList[i].sentences}, score is ${cleanResultList[i].score}, rank is ${cleanResultList[i].rank}")
        }

        val searchResultRecordsDB : SearchResultRecordsDB = Room.databaseBuilder(
            applicationContext,
            SearchResultRecordsDB::class.java, "searchResultRecords.db"
        ).allowMainThreadQueries()
            .build()

        searchResultRecordsDB.searchResultRecordsDB().insert(
            SearchResultsRecordCustomClass("${cleanSearchEditText.text}", cleanResultList)
        )
    }
}