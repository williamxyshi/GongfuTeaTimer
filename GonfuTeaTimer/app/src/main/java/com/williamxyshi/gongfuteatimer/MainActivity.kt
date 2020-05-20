package com.williamxyshi.gongfuteatimer

import android.R
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import com.williamxyshi.gongfuteatimer.R as AppR


class MainActivity : AppCompatActivity() {


    private val START_TIME_IN_MILLIS: Long = 600000
    private var mTextViewCountDown: TextView? = null
    private var mButtonStartPause: Button? = null
    private var mButtonReset: Button? = null
    private var mCountDownTimer: CountDownTimer? = null
    private var mTimerRunning = false
    private var mTimeLeftInMillis = START_TIME_IN_MILLIS

    private val brewArray: MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(AppR.layout.activity_main)
        mTextViewCountDown = findViewById(AppR.id.timeLeft)
        mButtonStartPause = findViewById(AppR.id.button)
        mButtonReset = findViewById(AppR.id.button2)
        mButtonStartPause?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (mTimerRunning) {
                    pauseTimer()
                } else {
                    startTimer()
                }
            }
        })
        mButtonReset?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                resetTimer()
            }
        })
        updateCountDownText()
    }

    private fun startTimer() {
        mCountDownTimer = object : CountDownTimer(mTimeLeftInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTimeLeftInMillis = millisUntilFinished
                updateCountDownText()
            }

            override fun onFinish() {
                mTimerRunning = false
                mButtonStartPause?.setText("Start")
                mButtonStartPause?.setVisibility(View.INVISIBLE)
                mButtonReset?.setVisibility(View.VISIBLE)
            }
        }.start()
        mTimerRunning = true
        mButtonStartPause?.setText("pause")
        mButtonReset?.setVisibility(View.INVISIBLE)
    }

    private fun pauseTimer() {
        mCountDownTimer!!.cancel()
        mTimerRunning = false
        mButtonStartPause?.setText("Start")
        mButtonReset?.setVisibility(View.VISIBLE)
    }

    private fun resetTimer() {
        mTimeLeftInMillis = START_TIME_IN_MILLIS
        updateCountDownText()
        mButtonReset?.setVisibility(View.INVISIBLE)
        mButtonStartPause?.setVisibility(View.VISIBLE)
    }

    private fun setBrewIntervals(){

    }

    private fun updateCountDownText() {
        val minutes = (mTimeLeftInMillis / 1000).toInt() / 60
        val seconds = (mTimeLeftInMillis / 1000).toInt() % 60
        val timeLeftFormatted: String =
            java.lang.String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds)
        mTextViewCountDown!!.text = timeLeftFormatted
    }
}
