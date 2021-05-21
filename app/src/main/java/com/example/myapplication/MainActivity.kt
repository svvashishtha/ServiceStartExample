package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.start_service).setOnClickListener {
            val intent = Intent(this@MainActivity, MyService::class.java)
            intent.action = MyService.START_SERVICE
            startService(intent)
        }
        findViewById<Button>(R.id.foreground).setOnClickListener {
            val intent = Intent(this@MainActivity, MyService::class.java)
            intent.action = MyService.FOREGROUND_SERVICE
            ContextCompat.startForegroundService(
                this@MainActivity,
                intent
            )
        }
        findViewById<Button>(R.id.stop_service).setOnClickListener {
            val intent = Intent(this@MainActivity, MyService::class.java)
            intent.action = MyService.STOP_SERVICE
            startService(intent)
        }

    }
}