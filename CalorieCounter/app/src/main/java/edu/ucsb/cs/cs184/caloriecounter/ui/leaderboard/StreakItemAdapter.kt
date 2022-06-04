package edu.ucsb.cs.cs184.caloriecounter.ui.leaderboard

import android.content.res.Resources
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import edu.ucsb.cs.cs184.caloriecounter.R

class StreakItemAdapter(private val itemList: List<StreakItemViewModel>) : RecyclerView.Adapter<StreakItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.findViewById(R.id.textName)
        val streakView: TextView = view.findViewById(R.id.textStreak)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.card_view_design, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val StreakItemViewModel = itemList[position]
        val spannable = SpannableString(StreakItemViewModel.streak + " day streak!")
        spannable.setSpan(ForegroundColorSpan(Color.rgb(209,88,118)), 0,StreakItemViewModel.streak.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE )
        holder.nameView.text = StreakItemViewModel.name + " just shared:"
        holder.streakView.text = spannable

    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}