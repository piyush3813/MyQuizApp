package com.example.myquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val editText = findViewById<EditText>(R.id.et_name)
        val btnStart = findViewById<Button>(R.id.buttonStart)

        btnStart.setOnClickListener {
            if (editText.text.isEmpty()) {
                Toast.makeText(this, "Please enter yor name", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, QuizQuestionActivity::class.java)
                intent.putExtra(Constants.USERNAME,editText.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }
}