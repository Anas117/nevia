package com.example.myapplication.user.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.service.dto.BookingDto

class BookingAdapter(
    var dataList: List<BookingDto>,
    var context: Context
) : RecyclerView.Adapter<BookingAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.list_booking,
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: BookingDto = dataList[position]
        holder.bindView(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(it: List<BookingDto>) {
        dataList = it
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {

        private var nameView: TextView = itemLayoutView.findViewById(R.id.item_name_room)
        private var timeView: TextView = itemLayoutView.findViewById(R.id.item_time)
        private var dayView: TextView = itemLayoutView.findViewById(R.id.item_day)

        @SuppressLint("SetTextI18n")
        fun bindView(bookingDto: BookingDto) {
            nameView.text = "Nom de la salle : " + bookingDto.roomName
            timeView.text = "Heure de début : " + bookingDto.startTime + " - Heure de fin : " + bookingDto.endTime
        }
    }
}