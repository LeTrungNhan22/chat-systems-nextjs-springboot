package com.snow.oauth2.socialoauth2.dto.mapper;

import com.snow.oauth2.socialoauth2.dto.request.chat.ChatDto;
import com.snow.oauth2.socialoauth2.model.chat.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Mapping(target = "senderId", source = "sender.id")
    @Mapping(target = "messageContent", source = "messageContent")
    @Mapping(target = "type", source = "messageType")
    ChatDto.MessageDto messageToMessageDto(Message message);

    @Mapping(target = "sender.id", source = "senderId")
    @Mapping(target = "messageContent", source = "messageContent")
    @Mapping(target = "messageType", source = "type")
    Message messageDtoToMessage(ChatDto.MessageDto messageDto);
}