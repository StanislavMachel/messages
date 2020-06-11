package com.example.messages.services

import com.example.messages.dtos.GetLatestMessageDto
import com.example.messages.dtos.GetMessageDto
import com.example.messages.dtos.PostMessageDto
import com.example.messages.model.Message
import com.example.messages.repositories.MessageRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.springframework.stereotype.Repository
import java.time.ZonedDateTime
import java.util.*

internal class MessageServiceImplTest {

    @InjectMocks
    lateinit var messageService: MessageServiceImpl

    @Mock
    lateinit var messageRepository: MessageRepository

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun fetchAll() {

        val messages: List<Message> = listOf(
                createMessage("Message 1"),
                createMessage("Message 2")
        )

        Mockito.`when`(messageRepository.findAll()).thenReturn(messages)

        val messageDtos = messageService.fetchAll()

        assertEquals(2, messageDtos.size)
        verify(messageRepository, times(1)).findAll()
    }

    @Test
    fun create() {

        Mockito.`when`(messageRepository.save(any(Message::class.java))).thenReturn(createMessage("Message 1"))

        messageService.create(PostMessageDto("Message 1"))

        verify(messageRepository, times(1)).save(any(Message::class.java))

    }

    @Test
    fun fetchLatest() {
        Mockito.`when`(messageRepository.count()).thenReturn(20)
        Mockito.`when`(messageRepository.findFirstByOrderByCreatedDesc()).thenReturn(createMessage("Message 1"))

        val getLatestMessageDto = messageService.fetchLatest()

        verify(messageRepository, times(1)).count()
        verify(messageRepository, times(1)).findFirstByOrderByCreatedDesc()

        assertNotNull(getLatestMessageDto.latest)
        assertNotNull(getLatestMessageDto.total)
        assertEquals("Message 1", getLatestMessageDto.latest?.content)
        assertEquals(20, getLatestMessageDto.total)
    }

    private fun createMessage(content: String) : Message{
        val message = Message(content)
        message.id = UUID.randomUUID()
        message.created = ZonedDateTime.now()
        return message
    }
}