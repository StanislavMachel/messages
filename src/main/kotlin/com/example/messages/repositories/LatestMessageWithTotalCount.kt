package com.example.messages.repositories

import java.time.ZonedDateTime
import java.util.*

data class LatestMessageWithTotalCount(
        var id: UUID,
        var content: String,
        var created: ZonedDateTime,
        var totalCount: Long
)