package com.example.messages.dtos

import java.time.ZonedDateTime
import java.util.*

data class GetLatestMessageDto(
        val latest: GetMessageDto?,
        val total: Long
)