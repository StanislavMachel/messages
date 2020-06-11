package com.example.messages.controllers

import com.example.messages.dtos.GetLatestMessageDto
import com.example.messages.dtos.GetMessageDto
import com.example.messages.dtos.PostMessageDto
import com.example.messages.model.Message
import com.example.messages.services.MessageService
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.*
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.ZonedDateTime
import java.util.*

internal class MessageControllerTest {

    @InjectMocks
    lateinit var messageController: MessageController

    @Mock
    lateinit var messageServiceMock: MessageService
    lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mockMvc = MockMvcBuilders.standaloneSetup(messageController).build()
    }

    @Test
    fun fetchAll() {
        val firstItemId = UUID.randomUUID()
        val firstItemContent = "Message 1"

        val getMessageDto: Set<GetMessageDto> = hashSetOf(
                GetMessageDto(firstItemId, firstItemContent, ZonedDateTime.now()),
                GetMessageDto(UUID.randomUUID(), "Message 2", ZonedDateTime.now())
        )

        Mockito.`when`(messageServiceMock.fetchAll()).thenReturn(getMessageDto)

        mockMvc.perform(get("/api/messages"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize<Any>(2)))
                .andExpect(jsonPath("$[0].id", `is`(firstItemId.toString())))
                .andExpect(jsonPath("$[0].content", `is`(firstItemContent)))


        verify(messageServiceMock, times(1)).fetchAll()
    }

    @Test
    fun fetchLatest() {
        Mockito.`when`(messageServiceMock.fetchLatest())
                .thenReturn(GetLatestMessageDto(GetMessageDto(UUID.randomUUID(), "Message 1", ZonedDateTime.now()), 10L))

        mockMvc.perform(get("/api/messages/latest"))
                .andExpect(status().isOk)

        verify(messageServiceMock, times(1)).fetchLatest()
    }

    @Test
    fun create() {

        val postMessageDto = PostMessageDto("Message 1")

        Mockito.`when`(messageServiceMock.create(postMessageDto))
                .thenReturn(GetMessageDto(UUID.randomUUID(), "Message 1", ZonedDateTime.now()))


        mockMvc.perform(post("/api/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postMessageDto)))
                .andExpect(status().isOk)

    }

    @Test
    fun create400() {

        val postMessageDto = PostMessageDto("Message 1")
        postMessageDto.content = ""

        Mockito.`when`(messageServiceMock.create(postMessageDto))
                .thenReturn(GetMessageDto(UUID.randomUUID(), "Message 1", ZonedDateTime.now()))


        mockMvc.perform(post("/api/messages")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(postMessageDto)))
                .andExpect(status().isBadRequest)

    }

    private fun asJsonString(obj: Any): String {
        try {
            return ObjectMapper().writeValueAsString(obj)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}