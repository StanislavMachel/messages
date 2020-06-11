package com.example.messages.bootstrap

import com.example.messages.model.Message
import com.example.messages.repositories.MessageRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import java.time.ZonedDateTime
import java.util.*

@Component
class Bootstrap(private val messageRepository: MessageRepository) : CommandLineRunner {
    override fun run(vararg args: String?) {

        for(i in 1..100){
            messageRepository.save(Message("Message $i"))
        }
    }
}