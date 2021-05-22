package com.example.myapplication

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    var mService: MyService? = null
    var mBound = false
    val TAG = "MainActivity"
    private val connection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(
            className: ComponentName,
            service: IBinder
        ) {
            // We've bound to MyService, cast the IBinder and get MyService instance
            val binder: MyService.LocalBinder = service as MyService.LocalBinder
            mService = binder.getService()
            mBound = true
            bringServiceToForeground()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.start_service_smartly).setOnClickListener {
            val intent = Intent(this@MainActivity, MyService::class.java)
            intent.action = MyService.START_SERVICE
            startService(intent)
            bindWithService()
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

    override fun onPause() {
        super.onPause()
        if (mBound) {
            unbindService(connection)
            mBound = false
        }
    }

    private fun bindWithService() {
        val intent = Intent(this, MyService::class.java)
        bindService(intent, connection, BIND_IMPORTANT)
    }


    private fun bringServiceToForeground() {
        mService?.let {
            if (!it.isForeGroundService) {
                val intent = Intent(this, MyService::class.java)
                intent.action = MyService.FOREGROUND_SERVICE
                ContextCompat.startForegroundService(this, intent)
                mService!!.doForegroundThings()
            } else {
                Log.d(TAG, "Service is already in foreground")
            }
        } ?: Log.d(TAG, "Service is null")

    }
}