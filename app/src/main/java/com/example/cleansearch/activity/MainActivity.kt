package com.example.cleansearch.activity

import android.content.Context
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
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cleansearch.R
import com.example.cleansearch.adapter.FieldWordRecyclerViewAdapter
import com.example.cleansearch.adapter.KeyWordRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), KeyWordRecyclerViewAdapter.ItemViewSetOnLongClickListener {

    var spinnerList : ArrayList<String> = arrayListOf("선택", "개발", "과학", "역사", "수학", "공부", "진로", "건강", "운동")
    var spinnerAdapter : ArrayAdapter<String>? = null
    var selectField : String = "선택"
    var fieldWordList : ArrayList<String> = arrayListOf()
    var fieldWordAdapter : FieldWordRecyclerViewAdapter = FieldWordRecyclerViewAdapter(fieldWordList)
    var keyWordList : ArrayList<String> = arrayListOf()
    var keyWordAdapter : KeyWordRecyclerViewAdapter? = null
    var selectBrowserText : String = "NAVER"

    var keyWordLottieAnimationBool = false

    lateinit var KeyWordDialog: AlertDialog.Builder
    lateinit var KeyWordEdialog: LayoutInflater
    lateinit var KeyWordMView: View
    lateinit var KeyWordBuilder: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE or
                View.SYSTEM_UI_FLAG_FULLSCREEN or
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

        keyWordAdapter = KeyWordRecyclerViewAdapter(keyWordList, this)

        val fieldRecyclerViewLayoutManager = GridLayoutManager(applicationContext, 2)
        val keyWordRecyclerViewLayoutManager = GridLayoutManager(applicationContext, 2)

        //분야에 맞는 단어들을 정의하는 부분.
        val fieldWordMap = mapOf<String, ArrayList<String>>("선택" to arrayListOf(),
        "개발" to arrayListOf("stackOverFlow", "StackOverFlow", "개발자", "개발"), "과학" to arrayListOf("돌", "화산", "지진", "화석", "화성", "우주", "지구", "땅", "물", "불", "흙", "공기"),
        "역사" to arrayListOf("지리", "역사"), "수학" to arrayListOf("더하기", "나누기", "빼기", "곱하기"), "공부" to arrayListOf("과목", "학교"), "진로" to arrayListOf("꿈", "대학교", "취직", "취업"),
        "건강" to arrayListOf("건강", "양파"), "운동" to arrayListOf("축구", "농구", "야구", "배구", "배드민턴", "탁구"))

        //스피너 관련 코드
        spinnerAdapter = ArrayAdapter(this,
            R.layout.item_field_spinner, spinnerList)
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
                if(position != 0)
                {
                    if(selectFieldLottieAnimationView.visibility == View.VISIBLE)
                    {
                        val animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.lottie_animation_alpha_gone_animation)
                        selectFieldLottieAnimationView.startAnimation(animation)
                        Handler().postDelayed({
                            selectFieldLottieAnimationView.visibility = View.GONE
                        }, 500)
                    }

                }
                else if (position == 0)
                {
                    val animation = AnimationUtils.loadAnimation(this@MainActivity, R.anim.lottie_animation_alpha_visible_animation)
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
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            if(KeyWordEditText.text.isNotEmpty())
            {
                keyWordList.add(KeyWordEditText.text.toString())
                keyWordAdapter!!.notifyDataSetChanged()
                KeyWordEditText.text = null
                if(keyWordList.size == 1)
                {
                    keyWordLottieAnimationBool = true
                }
                if(keyWordLottieAnimationBool == true)
                {
                    val animation = AnimationUtils.loadAnimation(this, R.anim.lottie_animation_alpha_gone_animation)
                    noKeyWordLottieAnimation.startAnimation(animation)
                    Handler().postDelayed({
                        noKeyWordLottieAnimation.visibility = View.GONE
                    }, 500)
                    keyWordLottieAnimationBool = false
                }
            }
            else if(KeyWordEditText.text.isEmpty())
            {
                Toast.makeText(applicationContext, "키워드를 입력해주세요", Toast.LENGTH_LONG).show()
            }
        }

        naverBrowserLayout.setOnClickListener {
            selectBrowserText = "NAVER"
            selectBrowserTextView.setText("검색 브라우저* (현재 브라우저 : $selectBrowserText)")
            naverBrowserLayout.setBackgroundResource(R.drawable.select_browser_background)
            googleBrowserLayout.setBackgroundResource(R.drawable.no_select_browser_background)
            daumBrowserLayout.setBackgroundResource(R.drawable.no_select_browser_background)
        }

        googleBrowserLayout.setOnClickListener {
            selectBrowserText = "GOOGLE"
            selectBrowserTextView.setText("검색 브라우저* (현재 브라우저 : $selectBrowserText)")
            googleBrowserLayout.setBackgroundResource(R.drawable.select_browser_background)
            naverBrowserLayout.setBackgroundResource(R.drawable.no_select_browser_background)
            daumBrowserLayout.setBackgroundResource(R.drawable.no_select_browser_background)
        }

        daumBrowserLayout.setOnClickListener {
            selectBrowserText = "DAUM"
            selectBrowserTextView.setText("검색 브라우저* (현재 브라우저 : $selectBrowserText)")
            daumBrowserLayout.setBackgroundResource(R.drawable.select_browser_background)
            naverBrowserLayout.setBackgroundResource(R.drawable.no_select_browser_background)
            googleBrowserLayout.setBackgroundResource(R.drawable.no_select_browser_background)
        }
    }

    //keyWord 가 롱클릭 됬을 때.
    override fun itemViewLongClick(position: Int) {
        KeyWordDialog = AlertDialog.Builder(this)
        KeyWordEdialog = LayoutInflater.from(this)
        KeyWordMView = KeyWordEdialog.inflate(R.layout.key_word_item_remove_dialog, null)
        KeyWordBuilder = KeyWordDialog.create()

        val KeyWordRemoveButtonDialog = KeyWordMView.findViewById<Button>(R.id.keyWordRemoveButtonDialog)
        val KeyWordCancelButtonDialog = KeyWordMView.findViewById<Button>(R.id.keyWordRemoveCancelButtonDialog)

        KeyWordBuilder.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        KeyWordBuilder.window?.requestFeature(Window.FEATURE_NO_TITLE)

        KeyWordBuilder.setView(KeyWordMView)
        KeyWordBuilder.show()

        KeyWordRemoveButtonDialog.setOnClickListener {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            keyWordList.removeAt(position)
            if(keyWordList.size == 0)
            {
                val animation = AnimationUtils.loadAnimation(this, R.anim.lottie_animation_alpha_visible_animation)
                noKeyWordLottieAnimation.visibility = View.VISIBLE
                noKeyWordLottieAnimation.startAnimation(animation)
            }
            keyWordAdapter!!.notifyDataSetChanged()
            KeyWordBuilder.dismiss()
        }

        KeyWordCancelButtonDialog.setOnClickListener {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE or
                    View.SYSTEM_UI_FLAG_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            KeyWordBuilder.dismiss()
        }

    }
}