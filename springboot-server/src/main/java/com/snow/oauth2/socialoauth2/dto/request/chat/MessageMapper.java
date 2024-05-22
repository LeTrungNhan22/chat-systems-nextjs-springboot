package com.snow.oauth2.socialoauth2.dto.request.chat;

import com.snow.oauth2.socialoauth2.model.chat.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    ChatDto.MessageDto messageToMessageDto(Message message);
}