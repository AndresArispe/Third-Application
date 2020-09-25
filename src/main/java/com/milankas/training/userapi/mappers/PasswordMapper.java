package com.milankas.training.userapi.mappers;

import com.milankas.training.userapi.dto.PasswordDto;
import com.milankas.training.userapi.persistance.model.Password;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PasswordMapper {

    PasswordDto passwordToDto(Password password);

    List<PasswordDto> toPasswordsDto(List<Password> passwords);

    Password toPassword(PasswordDto passwordDto);
}
