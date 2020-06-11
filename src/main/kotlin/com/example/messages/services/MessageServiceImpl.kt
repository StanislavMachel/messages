package com.example.messages.services

import com.example.messages.dtos.GetLatestMessageDto
import com.example.messages.dtos.GetMessageDto
import com.example.messages.dtos.PostMessageDto
import com.example.messages.model.Message
import com.example.messages.repositories.MessageRepository
import org.springframework.stereotype.Service

@Service
class MessageServiceImpl(private val messageRepository: MessageRepository) : MessageService {

    override fun fetchAll(): Set<GetMessageDto> {
        return messageRepository.findAll()
                .map { message: Message -> GetMessageDto(message.id!!, message.content, message.created) }
                .toSet()
    }

    override fun create(postMessageDto: PostMessageDto): GetMessageDto {
        val message = messageRepository.save(Message(postMessageDto.content))
        return GetMessageDto(message.id!!, message.content, message.created)
    }

    override fun fetchLatest(): GetLatestMessageDto {
        val count = messageRepository.count()
        val latestMessage = messageRepository.findFirstByOrderByCreatedDesc()

        val latestMessageDto = if (latestMessage != null) GetMessageDto(latestMessage.id!!, latestMessage.content, latestMessage.created) else null

        return GetLatestMessageDto(latestMessageDto, count)
    }
}