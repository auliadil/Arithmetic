package com.adilstudio.project.arithmetic.ui.mainmenu

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.adilstudio.project.arithmetic.R
import com.adilstudio.project.arithmetic.ui.game.GameActivity
import com.adilstudio.project.arithmetic.ui.login.LoggedInUserView
import com.adilstudio.project.arithmetic.ui.scoreboard.ScoreboardActivity

class MainMenuActivity : AppCompatActivity() {

    private var user: LoggedInUserView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        user = intent.getParcelableExtra(EXTRA_USER) as LoggedInUserView
    }

    fun openNewGameDialog(view: View) {

        val items = resources.getStringArray(R.array.level)
        val builder = AlertDialog.Builder(this)

        builder.setTitle(R.string.level_label)
        builder.setSingleChoiceItems(items, -1) { dialogInterface, i ->
            newGame(i)
            dialogInterface.dismiss()
        }

        builder.setNeutralButton(resources.getString(R.string.cancel)) { dialog, which ->
            dialog.cancel()
        }

        val mDialog = builder.create()
        mDialog.show()
    }

    private fun newGame(i: Int) {
        val intent = Intent(applicationContext, GameActivity::class.java)
        intent.putExtra("KEY_LEVEL", i)
        startActivity(intent)
    }

    fun openScoreboard(view: View) {
        val intent = Intent(applicationContext, ScoreboardActivity::class.java)
        startActivity(intent)
    }

    companion object {
        private const val EXTRA_USER = "extra_user_data"

        fun start(activity: Activity, user: LoggedInUserView) {
            val intent = Intent(activity, GameActivity::class.java)
            intent.putExtra(EXTRA_USER, user)
            activity.startActivity(intent)
        }
    }

}

