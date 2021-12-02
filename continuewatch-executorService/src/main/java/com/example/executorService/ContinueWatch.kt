package com.example.executorService

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.lab6.R
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ContinueWatch : AppCompatActivity() {
    private var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView

    private var startedSleeping: Long = 0
    private var timeToSleep: Long = 1000

    private lateinit var executorService: ExecutorService

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
        executorService.shutdownNow()
        val curTime = System.currentTimeMillis()
        timeToSleep -= curTime - startedSleeping
    }


    override fun onResume() {
        super.onResume()
        if (secondsElapsed != 0) {
            textSecondsElapsed.post {
                textSecondsElapsed.text = getString(R.string.secondsElapsed, secondsElapsed)
            }
        }
        executorService = Executors.newSingleThreadExecutor()

        executorService.execute {
            while (true) {
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
        }
    }
}