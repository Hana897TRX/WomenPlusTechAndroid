package com.hana897trx.womenplustech.Adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.hana897trx.womenplustech.Models.Event
import com.hana897trx.womenplustech.Models.Messages
import com.hana897trx.womenplustech.R
import com.hana897trx.womenplustech.myCoursesMessages

class MessageAdapter (private val context: Context,
                      private val layout: Int,
                      private val dataSource: List<Messages>) : RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    class MessageViewHolder(
        inflater: LayoutInflater,
        parent: ViewGroup,
        layout: Int,
        context: Context
    ) : RecyclerView.ViewHolder(inflater.inflate(layout, parent, false)) {

        var txtMessage: TextView? = null
        var context : Context? = null

        init {
            this.context = context
            txtMessage = itemView.findViewById(R.id.txtMessage)
        }

        fun bindData(message: Messages) {
            txtMessage!!.text = message.message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MessageViewHolder(inflater, parent, layout, parent.context)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val elemento = dataSource[position]
        holder.bindData(elemento)
    }

    override fun getItemCount(): Int {
        return dataSource.size
    }
}