package com.wotin.cleansearch.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.wotin.cleansearch.Extensions.onEditTextChanged
import com.wotin.cleansearch.R
import kotlinx.android.synthetic.main.activity_add_synonym.*

class AddAndReplaceSynonymActivity : AppCompatActivity() {

    var synonymList : ArrayList<String> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_synonym)

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
            controlLottieAnimationVisible()
        }

        LeftImageViewAddSynonymActivity.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun controlLottieAnimationVisible() {
        if (synonymList.isEmpty()) synonymLottieAnimationLayout.visibility = View.GONE
        else synonymLottieAnimationLayout.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}