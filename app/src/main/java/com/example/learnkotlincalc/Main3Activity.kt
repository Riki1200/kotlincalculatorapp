package com.example.learnkotlincalc


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.js.*
class Main3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val btn = findViewById<Button>(R.id.back) as Button

        btn.setOnClickListener {
            val intent = Intent(this@Main3Activity,MainActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()
        val textView = findViewById<TextView>(R.id.text_date) as TextView
        val f = Date()
        textView.text = f.year.toString()
    }
}