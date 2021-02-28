package com.adilstudio.project.arithmetic.ui.game

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.adilstudio.project.arithmetic.R
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.adilstudio.project.arithmetic.data.model.Score
import com.adilstudio.project.arithmetic.helper.showTopToast
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*
import kotlin.collections.ArrayList


class GameActivity : AppCompatActivity() {

    var answers : MutableList<Int> = ArrayList()
    var wrongAnswer = 0
    var locationOfCorrectAnswer = 0
    var score = 0
    var numberOfQuestion = 0
    var bound = 0
    var level = 0

    private val database = Firebase.database
    private val reference = database.getReference("scores")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_chevron_left_black)

        val intent = intent
        level = intent.getIntExtra("KEY_LEVEL", 0)

        when (level) {
            0 -> bound = 9
            1 -> bound = 49
            2 -> bound = 99
        }

        start()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun start() {
        play(playAgainButton)
    }

    fun play(view: View) {
        gameLayout.visibility = View.VISIBLE
        score = 0
        numberOfQuestion = 0
        timerTextView.text = DEFAULT_TIME
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
        val rand = Random()
        val firstNumber = rand.nextInt(bound)
        val secondNumber = rand.nextInt(bound)

        sumTextView.text = getString(R.string.sum_text, firstNumber, secondNumber)

        locationOfCorrectAnswer = rand.nextInt(4)

        for (i in 0..3) {
            if (i == locationOfCorrectAnswer) {
                answers.add(firstNumber + secondNumber)
            }
            else {
                wrongAnswer = rand.nextInt(bound)
                while(wrongAnswer == firstNumber + secondNumber) {
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
        if(view.tag.toString() == locationOfCorrectAnswer.toString()) {
            resultTextView.text = getString(R.string.correct)
            score += 100
            Log.i("Correct!", "You got it!")
        }
        else {
            resultTextView.text = getString(R.string.wrong)
            Log.i("Wrong!", ":/")
        }
        numberOfQuestion++
        scoreTextView.text = "$score"
        newQuestion(bound)
    }

    fun saveScore(view: View) {
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val formsView: View = inflater.inflate(R.layout.dialog_save_score, null, false)

        val builder = AlertDialog.Builder(this)
        val name = formsView.findViewById<TextInputEditText>(R.id.nameEditText)
        builder.setTitle(resources.getString(R.string.save_your_score))
        builder.setView(formsView)
        builder.setPositiveButton(resources.getString(R.string.save)) { _, _ ->
            showTopToast(getString(R.string.name, name.text.toString()))
            writeNewScore(name.text.toString(), level.toString(), score.toString())
        }
        builder.setNeutralButton(resources.getString(R.string.cancel)) { dialog, _ ->
            dialog.cancel()
        }
        val mDialog = builder.create()
        mDialog.show()
    }

    private fun writeNewScore(name: String, level: String, score: String) {
        val id = UUID.randomUUID().toString()
        val score = Score(id, name, level, score)
        reference.child(id).setValue(score)
    }
    
    companion object {
        const val DEFAULT_TIME = "30"
    }
}
