package com.example.myapplication.user.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat.getDrawable
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.service.dto.SearchRoomDto

class RoomAdapter(
    private var dataList: List<SearchRoomDto>,
    var context: Context,
    private val listener: OnItemClickListener,
    var imagePickRoom: Drawable
) : RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.list_item_room,
                parent,
                false
            )
        )
    }

    interface OnItemClickListener {
        fun onItemClick(room: SearchRoomDto)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item: SearchRoomDto = dataList[position]
        holder.bindView(item, listener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun update(it: List<SearchRoomDto>) {
        dataList = it
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        // voir ce que fait inner class
        private var nameRoomView: TextView = itemLayoutView.findViewById(R.id.name_room)

        @SuppressLint("SetTextI18n")
        fun bindView(searchRoomDto: SearchRoomDto, listener: OnItemClickListener) {
            nameRoomView.text = "Nom : " + searchRoomDto.nameRoom

            itemView.setOnClickListener { listener.onItemClick(searchRoomDto) }

        }
    }
}