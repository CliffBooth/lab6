package com.example.thread

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.lab6.R

class ContinueWatch : AppCompatActivity() {
    private var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView

    private var startedSleeping: Long = 0
    private var timeToSleep: Long = 1000

    private val runnable: () -> Unit = {
        Log.i("mythread", "${Thread.currentThread().id} created!")
        while (!Thread.interrupted()) {
            try {
                startedSleeping = System.currentTimeMillis()
                Thread.sleep(timeToSleep)
                timeToSleep = 1000
                textSecondsElapsed.post {
                    textSecondsElapsed.text =
                        getString(R.string.secondsElapsed, ++secondsElapsed)
                }
            } catch (e: InterruptedException) {
                break
            }
        }
        Log.i("mythread", "${Thread.currentThread().id} exits")
    }

    private lateinit var backgroundThread: Thread

    companion object {
        const val STATE_SECONDS = "secondsElapsed"
        const val TO_SLEEP = "sleep"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_continue_watch)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(STATE_SECONDS, secondsElapsed)
        outState.putLong(TO_SLEEP, timeToSleep)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        secondsElapsed = savedInstanceState.getInt(STATE_SECONDS)
        timeToSleep = savedInstanceState.getLong(TO_SLEEP)
    }


    override fun onPause() {
        super.onPause()
        backgroundThread.interrupt()
        val curTime = System.currentTimeMillis()
        timeToSleep -= curTime - startedSleeping
    }


    override fun onResume() {
        super.onResume()
        if (secondsElapsed != 0) {
            textSecondsElapsed.text = getString(R.string.secondsElapsed, secondsElapsed)
        }
        backgroundThread = Thread(runnable)
        backgroundThread.start()
    }
}
