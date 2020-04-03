package com.muhammadauliaadil.project.arithmetic

import android.content.DialogInterface
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*
import kotlin.collections.ArrayList


class GameActivity : AppCompatActivity() {

    var a = 5
    var b = 2
    var answers : MutableList<Int> = ArrayList()
    var wrongAnswer = 0
    var locationOfCorrectAnswer = 0
    var score = 0
    var numberOfQuestion = 0
    var bound = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_chevron_left_black)

        val intent = intent
        val level = intent.getIntExtra("KEY_LEVEL", 0)

        when {
            level == 0 -> {
                bound = 9
            }
            level == 1 -> {
                bound = 49
            }
            level == 2 -> {
                bound = 99
            }
        }

        start()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun start() {
        play(playAgainButton)
    }

    fun play(view: View) {
        gameLayout.visibility = View.VISIBLE
        score = 0
        numberOfQuestion = 0
        timerTextView.text = "30"
        scoreTextView.text = "$score"
        newQuestion(bound)

        object: CountDownTimer(30100, 1000) {
            override fun onFinish() {
                gameLayout.visibility = View.INVISIBLE
                scoreResult.text = score.toString()
                resultLayout.visibility = View.VISIBLE
            }

            override fun onTick(millisUntilFinished: Long) {
                val text= millisUntilFinished/1000
                timerTextView.text = text.toString()
            }

        }.start()

        resultLayout.visibility = View.INVISIBLE
    }

    fun backToHome (view : View) {
        val builder = AlertDialog.Builder(this)

        builder.setTitle(resources.getString(R.string.back_to_home))
        builder.setMessage(resources.getString(R.string.are_you_sure))
        builder.setPositiveButton(resources.getString(R.string.yes)) {
            dialog, which -> finish()
        }
        builder.setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
            dialog.cancel()
        }

        finish()
    }

    private fun newQuestion(bound : Int) {
        answers.clear()
        var rand = Random()
        a = rand.nextInt(bound)
        b = rand.nextInt(bound)

        sumTextView.text = "$a + $b"

        locationOfCorrectAnswer = rand.nextInt(4)

        for (i in 0..3) {
            if (i == locationOfCorrectAnswer) {
                answers.add(a + b)
            }
            else {
                wrongAnswer = rand.nextInt(bound)
                while(wrongAnswer == a + b) {
                    wrongAnswer = rand.nextInt(bound)
                }
                answers.add(wrongAnswer)
            }
        }

        button0.text = answers[0].toString()
        button1.text = answers[1].toString()
        button2.text = answers[2].toString()
        button3.text = answers[3].toString()
    }

    fun chooseAnswer(view : View) {
        if(view.tag.toString().equals(locationOfCorrectAnswer.toString())) {
            resultTextView.text = resources.getString(R.string.correct)
            score += 100
            Log.i("Correct!", "You got it!")
        }
        else {
            resultTextView.text = resources.getString(R.string.wrong)
            Log.i("Wrong!", ":/")
        }
        numberOfQuestion++
        scoreTextView.text = "$score"
        newQuestion(bound)
    }

    fun saveScore() {

        // Write a message to the database
        val database = Firebase.database
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")
    }
}
