package com.example.messages.repositories

import com.example.messages.model.Message
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MessageRepository : JpaRepository<Message, UUID> {
    fun findFirstByOrderByCreatedDesc(): Message?
}