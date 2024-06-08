package com.snow.oauth2.socialoauth2.dto.mapper;


import com.snow.oauth2.socialoauth2.dto.request.chat.ChatDto;
import com.snow.oauth2.socialoauth2.model.chat.Chat;
import com.snow.oauth2.socialoauth2.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChatMapper {
    ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

    @Mapping(target = "participants", source = "participants", qualifiedByName = "mapParticipants")
    @Mapping(target = "adminId", source = "admin.id")
    ChatDto chatToChatDto(Chat chat);

    @Named("mapParticipants")
    List<ChatDto.ParticipantDto> mapParticipants(List<User> participants);


}


