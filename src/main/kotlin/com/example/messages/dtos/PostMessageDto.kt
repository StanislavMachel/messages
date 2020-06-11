package com.example.messages.dtos

import javax.validation.constraints.NotBlank

data class PostMessageDto(@field:NotBlank var content: String)