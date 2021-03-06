package com.example.messages.controllers

import com.example.messages.dtos.GetLatestMessageDto
import com.example.messages.dtos.GetMessageDto
import com.example.messages.dtos.PostMessageDto
import com.example.messages.services.MessageService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/messages")
@RestController
class MessageController(private val messageService: MessageService) {

    @GetMapping
    fun fetchAll(): ResponseEntity<Iterable<GetMessageDto>> {
        return ResponseEntity.ok(messageService.fetchAll())
    }

    @GetMapping("/latest")
    fun fetchLatest(): ResponseEntity<GetLatestMessageDto> {
        return ResponseEntity.ok(messageService.fetchLatest())
    }

    @PostMapping
    fun create(@Valid @RequestBody postMessageDto: PostMessageDto): ResponseEntity<GetMessageDto> {
        return ResponseEntity.ok(messageService.create(postMessageDto))
    }
}