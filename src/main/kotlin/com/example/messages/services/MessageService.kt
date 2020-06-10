package com.example.messages.services

import com.example.messages.dtos.GetLatestMessageDto
import com.example.messages.dtos.GetMessageDto
import com.example.messages.dtos.PostMessageDto

interface MessageService {
    fun fetchAll(): Set<GetMessageDto>
    fun create(postMessageDto: PostMessageDto): GetMessageDto
    fun fetchLatest() : GetLatestMessageDto
}