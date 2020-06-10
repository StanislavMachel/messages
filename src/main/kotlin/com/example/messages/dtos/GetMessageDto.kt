package com.example.messages.dtos

import java.time.ZonedDateTime
import java.util.*

data class GetMessageDto(
        val id: UUID,
        var content: String,
        var created: ZonedDateTime
)