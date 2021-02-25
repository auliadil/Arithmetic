package com.adilstudio.project.arithmetic

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainMenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
    }

    fun openNewGameDialog(view : View) {

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

    fun openScoreboard(view : View) {
        val intent = Intent(applicationContext, ScoreboardActivity::class.java)
        startActivity(intent)
    }

}

