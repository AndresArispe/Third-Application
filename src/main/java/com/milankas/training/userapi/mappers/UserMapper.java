package com.milankas.training.userapi.mappers;

import com.milankas.training.userapi.dto.UserDto;
import com.milankas.training.userapi.persistance.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToDto(User user);

    List<UserDto> toUsersDto(List<User> users);

    User toUser(UserDto userDto);
}
