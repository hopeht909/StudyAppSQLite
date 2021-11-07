package com.example.appstudy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var btAndroid : Button
    lateinit var btKotlin : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btAndroid = findViewById(R.id.btAndroid)
        btKotlin = findViewById(R.id.btKotlin)

        btKotlin.setOnClickListener {
            val intent = Intent(this, KotlinActivity::class.java)
            startActivity(intent)
        }
        btAndroid.setOnClickListener {
            val intent = Intent(this, AndroidActivity::class.java)
            startActivity(intent)
        }
    }
}