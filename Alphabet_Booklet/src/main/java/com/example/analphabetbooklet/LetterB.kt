package com.example.analphabetbooklet

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class LetterB : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_letter_b)

        val btnFirst = findViewById<Button>(R.id.btnFP)
        val btnLast = findViewById<Button>(R.id.btnLP)

        //Toolbar
        val overIcon = findViewById<ImageView>(R.id.ovIcon)
        val overTitle = findViewById<TextView>(R.id.ovTitle)

        //Toolbar button
        overIcon.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }


        //get A class
        btnFirst.setOnClickListener{
            val intent = Intent(this,LetterA::class.java)
            startActivity(intent)

        }
    }
}