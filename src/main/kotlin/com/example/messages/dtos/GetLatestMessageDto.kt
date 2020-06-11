package com.example.messages.dtos

data class GetLatestMessageDto(
        val latest: GetMessageDto?,
        val total: Long
)