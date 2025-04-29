package com.example.chatbot.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chatbot.data.Message
import com.example.chatbot.databinding.MessageItemBinding
import com.example.chatbot.utils.Constants.SEND_ID

class MessagingAdapter: RecyclerView.Adapter<MessagingAdapter.MessageViewHolder>() {

    var messageList = mutableListOf<Message>()

    inner class MessageViewHolder(val binding: MessageItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = MessageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messageList[position]

        if (currentMessage.id == SEND_ID) {
            holder.binding.tvMessage.apply {
                text = currentMessage.message
                visibility = View.VISIBLE
            }
            holder.binding.tvBotMessage.visibility = View.GONE
        } else {
            holder.binding.tvBotMessage.apply {
                text = currentMessage.message
                visibility = View.VISIBLE
            }
            holder.binding.tvMessage.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
    fun insertMessage(message: Message) {
        this.messageList.add(message)
        notifyItemInserted(messageList.size)
    }

    fun setMessages(messages: List<Message>) {
        this.messageList.clear()
        this.messageList.addAll(messages)
        notifyDataSetChanged()
    }

}
