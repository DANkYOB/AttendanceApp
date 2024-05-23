package com.example.nelsonfinalyearproject.Adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nelsonfinalyearproject.Home.SubjectModel
import com.example.nelsonfinalyearproject.databinding.ItemTodayClassBinding
import java.util.Calendar

class TodayClassAdapter : RecyclerView.Adapter<TodayClassViewHolder>() {

    private val subjects = mutableListOf<SubjectModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayClassViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTodayClassBinding.inflate(layoutInflater, parent, false)
        val currentTime = System.currentTimeMillis()
        val midnightTime = getMidnightTimestamp()
        val weekDay = getWeekDay()
        Log.e("weekDay", weekDay.toString())

        for (sub in subjects) {
            Log.e("sub", sub.toString())
            val todayTimings = sub.timings?.get(weekDay)
            if (!todayTimings.isNullOrEmpty()) {
                for (slot in todayTimings) {
                    if (
                        currentTime > (slot[0] * 60 * 1000) + midnightTime &&
                        currentTime < (slot[1] * 60 * 1000) + midnightTime
                    ) {
                        //Class is ongoing
                    } else if (
                        currentTime > (slot[0] * 60 * 1000) + midnightTime &&
                        currentTime > (slot[1] * 60 * 1000) + midnightTime
                    ) {
                        //class ended
                    } else if (currentTime < (slot[0] * 60 * 1000) + midnightTime) {
                        //upcoming class
                    }
                }
            }
        }
        return TodayClassViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return subjects.size
    }

    override fun onBindViewHolder(holder: TodayClassViewHolder, position: Int) {
        holder.binding.tvSubject.text = subjects[position].name
        holder.binding.tvClassTime.text = subjects[position].timings.toString()
    }

    private fun getMidnightTimestamp(): Long {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.MILLISECOND, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        return calendar.timeInMillis
    }

    private fun getWeekDay(): Int {
        val calendar = Calendar.getInstance()
        return calendar.get(Calendar.DAY_OF_WEEK) - 2
    }

}


class TodayClassViewHolder(val binding: ItemTodayClassBinding) : RecyclerView.ViewHolder(binding.root) {

}

