package com.example.analphabetbooklet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.ViewAnimator
import androidx.appcompat.app.AppCompatActivity

class LetterA : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letter_a)

        val btnLast = findViewById<Button>(R.id.btnLP)
        val prevBtn = findViewById<Button>(R.id.btnPrev)
        val nxtBtn = findViewById<Button>(R.id.btnNext)
        val viewF = findViewById<ViewAnimator>(R.id.viewFlipper)
        val btnFirst = findViewById<Button>(R.id.btnFP2)

        //Toolbar
        val overIcon = findViewById<ImageView>(R.id.ovIcon)
        val overTitle = findViewById<TextView>(R.id.ovTitle)

        //Buttons listening from second activity
        if (savedInstanceState != null) {
            val flipperPosition = savedInstanceState.getInt("TAB_NUMBER")
            viewF.setDisplayedChild(flipperPosition)
        }
        else {
            val intent1 = intent
            val int1 = intent1.getIntExtra("intC", 0)
            viewF.displayedChild = int1
        }
        //toolbar
        overIcon.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        //navigation buttons
        prevBtn.setOnClickListener{
            viewF.showPrevious()
        }

        nxtBtn.setOnClickListener{
            viewF.showNext()
        }

       btnFirst.setOnClickListener{
           viewF.displayedChild = 0

        }

        btnLast.setOnClickListener{
            viewF.displayedChild = 25
        }


    }
    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        val viewF = findViewById<ViewAnimator>(R.id.viewFlipper)
        super.onSaveInstanceState(savedInstanceState)
        val position: Int = viewF.getDisplayedChild()
        savedInstanceState.putInt("TAB_NUMBER", position)
    }
}