package com.example.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.lab6.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ContinueWatch : AppCompatActivity() {
    private var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView

    private var startedSleeping: Long = 0
    private var timeToSleep: Long = 1000

    companion object {
        const val STATE_SECONDS = "secondsElapsed"
        const val TO_SLEEP = "sleep"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_continue_watch)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                while (true) {
                    startedSleeping = System.currentTimeMillis()
                    Log.i("my", "going to sleep: $timeToSleep")
                    delay(timeToSleep)
                    timeToSleep = 1000
                    textSecondsElapsed.text =
                        getString(R.string.secondsElapsed, ++secondsElapsed)
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val curTime = System.currentTimeMillis()
        timeToSleep -= curTime - startedSleeping
        outState.putInt(STATE_SECONDS, secondsElapsed)
        outState.putLong(TO_SLEEP, timeToSleep)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        secondsElapsed = savedInstanceState.getInt(STATE_SECONDS)
        timeToSleep = savedInstanceState.getLong(TO_SLEEP)
    }
}
