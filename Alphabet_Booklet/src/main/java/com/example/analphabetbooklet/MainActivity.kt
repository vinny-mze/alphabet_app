package com.example.analphabetbooklet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val txt1= findViewById<TextView>(R.id.txt)
        val btnA = findViewById<Button>(R.id.btn1)
        val btnB = findViewById<Button>(R.id.btn2)
        val btnC = findViewById<Button>(R.id.btn3)
        val btnD = findViewById<Button>(R.id.btn4)
        val btnE = findViewById<Button>(R.id.btn5)
        val btnF = findViewById<Button>(R.id.btn6)
        val btnG = findViewById<Button>(R.id.btn7)
        val btnH = findViewById<Button>(R.id.btn8)
        val btnI = findViewById<Button>(R.id.btn9)
        val btnJ = findViewById<Button>(R.id.btn10)
        val btnK = findViewById<Button>(R.id.btn11)
        val btnL = findViewById<Button>(R.id.btn12)
        val btnM = findViewById<Button>(R.id.btn13)
        val btnN = findViewById<Button>(R.id.btn14)
        val btnO = findViewById<Button>(R.id.btn15)
        val btnP = findViewById<Button>(R.id.btn16)
        val btnQ = findViewById<Button>(R.id.btn17)
        val btnR = findViewById<Button>(R.id.btn18)
        val btnS = findViewById<Button>(R.id.btn19)
        val btnT = findViewById<Button>(R.id.btn20)
        val btnU = findViewById<Button>(R.id.btn21)
        val btnV = findViewById<Button>(R.id.btn22)
        val btnW = findViewById<Button>(R.id.btn23)
        val btnX = findViewById<Button>(R.id.btn24)
        val btnY = findViewById<Button>(R.id.btn25)
        val btnZ = findViewById<Button>(R.id.btn26)
        val btnEx = findViewById<Button>(R.id.btn0)
        //get Letters class
        val intent = Intent(this,LetterA::class.java)

        btnA.setOnClickListener{
            intent.putExtra("intC", 0);
            startActivity(intent)
            }

        btnB.setOnClickListener{
            intent.putExtra("intC", 1);
            startActivity(intent)
        }

        btnC.setOnClickListener{
            intent.putExtra("intC", 2);
            startActivity(intent)
        }

        btnD.setOnClickListener{
            intent.putExtra("intC", 3);
            startActivity(intent)
        }

        btnE.setOnClickListener{
            intent.putExtra("intC", 4);
            startActivity(intent)
        }

        btnF.setOnClickListener{
            intent.putExtra("intC", 5);
            startActivity(intent)
        }

        btnG.setOnClickListener{
            intent.putExtra("intC", 6);
            startActivity(intent)
        }

        btnH.setOnClickListener{
            intent.putExtra("intC", 7);
            startActivity(intent)
        }

        btnI.setOnClickListener{
            intent.putExtra("intC", 8);
            startActivity(intent)
        }

        btnJ.setOnClickListener{
            intent.putExtra("intC", 9);
            startActivity(intent)
        }

        btnK.setOnClickListener{
            intent.putExtra("intC", 10);
            startActivity(intent)
        }

        btnL.setOnClickListener{
            intent.putExtra("intC", 11);
            startActivity(intent)
        }

        btnM.setOnClickListener{
            intent.putExtra("intC", 12);
            startActivity(intent)
        }

        btnN.setOnClickListener{
            intent.putExtra("intC", 13);
            startActivity(intent)
        }

        btnO.setOnClickListener{
            intent.putExtra("intC", 14);
            startActivity(intent)
        }

        btnP.setOnClickListener{
            intent.putExtra("intC", 15);
            startActivity(intent)
        }

        btnQ.setOnClickListener{
            intent.putExtra("intC", 16);
            startActivity(intent)
        }

        btnR.setOnClickListener{
            intent.putExtra("intC", 17);
            startActivity(intent)
        }

        btnS.setOnClickListener{
            intent.putExtra("intC", 18);
            startActivity(intent)
        }

        btnT.setOnClickListener{
            intent.putExtra("intC", 19);
            startActivity(intent)
        }

        btnU.setOnClickListener{
            intent.putExtra("intC", 20);
            startActivity(intent)
        }

        btnV.setOnClickListener{
            intent.putExtra("intC", 21);
            startActivity(intent)
        }

        btnW.setOnClickListener{
            intent.putExtra("intC", 22);
            startActivity(intent)
        }

        btnX.setOnClickListener{
            intent.putExtra("intC", 23);
            startActivity(intent)
        }

        btnY.setOnClickListener{
            intent.putExtra("intC", 24);
            startActivity(intent)
        }

        btnZ.setOnClickListener{
            intent.putExtra("intC", 25);
            startActivity(intent)
        }

        btnEx.setOnClickListener{
            finish()
            System.exit(0)
        }


    }
    }