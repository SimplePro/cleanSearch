package com.example.cleansearch.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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


    lateinit var KeyWordDialog: AlertDialog.Builder
    lateinit var KeyWordEdialog: LayoutInflater
    lateinit var KeyWordMView: View
    lateinit var KeyWordBuilder: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        keyWordAdapter = KeyWordRecyclerViewAdapter(keyWordList, this)

        val recyclerViewLayoutManager = GridLayoutManager(applicationContext, 2)

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
                TODO("Not yet implemented")
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if(position != 0)
                {
                    selectFieldLottieAnimationView.visibility = View.GONE
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
            layoutManager = recyclerViewLayoutManager
            setHasFixedSize(true)
        }
        //keyWord RecyclerView
        keywordRecyclerView.apply {
            adapter = keyWordAdapter
            layoutManager = recyclerViewLayoutManager
            setHasFixedSize(true)
        }
        //addKeyWordButton
        addKeyWordButton.setOnClickListener {
            if(KeyWordEditText.text.isNotEmpty())
            {
                keyWordList.add(KeyWordEditText.text.toString())
            }
            else if(KeyWordEditText.text.isEmpty())
            {
                Toast.makeText(applicationContext, "키워드를 입력해주세요", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun itemViewLongClick(position: Int) {
        KeyWordDialog = AlertDialog.Builder(this)
        KeyWordEdialog = LayoutInflater.from(this)
        KeyWordMView = KeyWordEdialog.inflate(R.layout.key_word_item_remove_dialog, null)
        KeyWordBuilder = KeyWordDialog.create()

        val KeyWordRemoveButtonDialog = KeyWordMView.findViewById<Button>(R.id.keyWordRemoveButtonDialog)
        val KeyWordCancelButtonDialog = KeyWordMView.findViewById<Button>(R.id.keyWordRemoveCancelButtonDialog)

        KeyWordBuilder.setView(KeyWordMView)
        KeyWordBuilder.show()

        KeyWordRemoveButtonDialog.setOnClickListener {
            keyWordList.removeAt(position)
            keyWordAdapter!!.notifyDataSetChanged()
            KeyWordBuilder.dismiss()
        }

        KeyWordCancelButtonDialog.setOnClickListener {
            KeyWordBuilder.dismiss()
        }

    }
}