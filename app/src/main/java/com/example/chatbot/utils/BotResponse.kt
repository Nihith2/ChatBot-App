package com.example.chatbot.utils

import com.example.chatbot.utils.Constants.OPEN_GOOGLE
import com.example.chatbot.utils.Constants.OPEN_SEARCH
import com.example.chatbot.utils.Constants.RECIEVE_ID
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

object BotResponse {
    fun basicResponses(_message: String): String {
        val random = (0..2).random()
        val message = _message.lowercase()

        return when {
            // Flip a coin
            message.contains("flip") && message.contains("coin") -> {
                val result = if ((0..1).random() == 0) "heads" else "tails"
                "I flipped a coin and it landed on $result"
            }


            // Solve math problems
            message.contains("solve") -> {
                val equation = message.substringAfterLast("solve")
                return try {
                    val answer = SolveMath.solveMath(equation)
                    "$answer"
                } catch (e: Exception) {
                    "Sorry, I can't solve that."
                }
            }

            // Greetings
            message.contains("hello") -> {
                listOf("Hello there!","hi buddy, how are you" ,"Sup", "Buongiorno!")[random]
            }

            // How are you?
            message.contains("how are you") -> {
                listOf("I'm doing fine, thanks!", "I'm hungry...", "Pretty good! How about you?","I am fine , how are you ")[random]
            }

            message.contains("who are you")->{
                listOf("I'm VisioBot , Your Assistant")[random]
            }

            // Get current time
            message.contains("time") && message.contains("?") -> {
                val timeStamp = Timestamp(System.currentTimeMillis())
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                sdf.format(Date(timeStamp.time)).toString()
            }

            // Open Google
            message.contains("open") && message.contains("google") -> OPEN_GOOGLE

            // Search something online
            message.contains("search") -> OPEN_SEARCH

            // Default response
            else -> listOf("I don't understand...", "Try asking me something different", "I dont know")[random]
        }
    }
}
