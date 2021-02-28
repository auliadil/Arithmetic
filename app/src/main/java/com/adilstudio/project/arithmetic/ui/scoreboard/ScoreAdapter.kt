package com.adilstudio.project.arithmetic.ui.scoreboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.adilstudio.project.arithmetic.R
import com.adilstudio.project.arithmetic.data.model.Score
import kotlinx.android.synthetic.main.item_score.view.*

class ScoreAdapter(private val scores: ArrayList<Score>) : RecyclerView.Adapter<ScoreAdapter.ScoreHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, p1: Int) = ScoreHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_score, parent, false)
    )

    override fun getItemCount(): Int {
        return scores.size
    }

    override fun onBindViewHolder(holder: ScoreHolder, position: Int) {
        holder.bind(scores[position])
    }


    class ScoreHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        private val scoreName = view.score_name

        fun bind(score: Score) {
            scoreName.text = score.player
        }
    }


}