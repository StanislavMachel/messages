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
        messageRepository.save(Message("Message 1"))
        messageRepository.save(Message("Message 2"))
        messageRepository.save(Message("Message 3"))
    }
}