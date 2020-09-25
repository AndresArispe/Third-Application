package com.milankas.training.userapi.dto;

import com.milankas.training.userapi.persistance.model.User;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PasswordDto {

    private UUID id;

    private String hash;
    private String salt;
    private int status;

    private User user;
}
