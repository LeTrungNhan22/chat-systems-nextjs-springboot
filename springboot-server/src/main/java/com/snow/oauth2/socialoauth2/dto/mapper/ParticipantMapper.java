package com.snow.oauth2.socialoauth2.dto.mapper;

import com.snow.oauth2.socialoauth2.dto.request.chat.ChatDto;
import com.snow.oauth2.socialoauth2.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ParticipantMapper {
    ParticipantMapper INSTANCE = Mappers.getMapper(ParticipantMapper.class);
    ChatDto.ParticipantDto userToParticipantDto(User user);
}