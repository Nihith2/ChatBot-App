package com.example.chatbot.ui


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatbot.R
import com.example.chatbot.data.Message
import com.example.chatbot.databinding.ActivityMainBinding
import com.example.chatbot.utils.BotResponse
import com.example.chatbot.utils.Constants.OPEN_GOOGLE
import com.example.chatbot.utils.Constants.OPEN_SEARCH
import com.example.chatbot.utils.Constants.RECIEVE_ID
import com.example.chatbot.utils.Constants.SEND_ID
import com.example.chatbot.utils.Time
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    var messagesList = mutableListOf<Message>()

    private lateinit var adapter: MessagingAdapter
    private val botList = listOf("VisioBot","Visio Bot")

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView()

        clickEvents()
        loadMessages()

        if (messagesList.isEmpty()) {
            val random = (0..1).random()
            customBotMessage("Hello! Today you're speaking with ${botList[random]}, how may I help?")
        }
    }

    private fun clickEvents() {

        //Send a message
        binding.btnSend.setOnClickListener {
            sendMessage()
        }
        binding.etMessage.setOnClickListener {
            lifecycleScope.launch {
                delay(100)

                withContext(Dispatchers.Main) {
                    binding.rvMessages.scrollToPosition(adapter.itemCount - 1)

                }
            }
        }
    }

    private fun recyclerView() {
        adapter = MessagingAdapter()
        binding.rvMessages.adapter = adapter
        binding.rvMessages.layoutManager = LinearLayoutManager(applicationContext)

    }

    override fun onStart() {
        super.onStart()
        //In case there are messages, scroll to bottom when re-opening app
        lifecycleScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun sendMessage() {
        val message = binding.etMessage.text.toString()
        val timeStamp = Time.timeStamp()

        if (message.isNotEmpty()) {
            //Adds it to our local list
            messagesList.add(Message(message, SEND_ID, timeStamp))
            binding.etMessage.setText("")

            adapter.insertMessage(Message(message, SEND_ID, timeStamp))
            binding.rvMessages.scrollToPosition(adapter.itemCount - 1)

            saveMessages()
            botResponse(message)
        }
    }

    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        lifecycleScope.launch {
            //Fake response delay
            delay(1000)

            withContext(Dispatchers.Main) {
                //Gets the response
                val response = BotResponse.basicResponses(message)

                //Adds it to our local list
                messagesList.add(Message(response, RECIEVE_ID, timeStamp))

                //Inserts our message into the adapter
                adapter.insertMessage(Message(response, RECIEVE_ID, timeStamp))

                //Scrolls us to the position of the latest message
                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)

                //Starts Google

                saveMessages()
                when (response) {
                    OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }

                }
            }
        }
    }

    private fun customBotMessage(message: String) {

        lifecycleScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                messagesList.add(Message(message, RECIEVE_ID, timeStamp))
                adapter.insertMessage(Message(message, RECIEVE_ID, timeStamp))

                binding.rvMessages.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun saveMessages() {
        val sharedPref = getSharedPreferences("chatbot_pref", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val gson = Gson()
        val json = gson.toJson(messagesList) // Convert list to JSON
        editor.putString("messages", json)
        editor.apply()
    }

    private fun loadMessages() {
        val sharedPref = getSharedPreferences("chatbot_pref", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPref.getString("messages", null)
        val type = object : TypeToken<MutableList<Message>>() {}.type
        if (json != null) {
            messagesList = gson.fromJson(json, type)
            adapter.setMessages(messagesList)
        }
    }
}