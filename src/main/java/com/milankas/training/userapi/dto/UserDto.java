package com.milankas.training.userapi.dto;

import com.milankas.training.userapi.persistance.model.Password;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserDto {

    private UUID id;

    private String firstName;
    private String lastName;
    private String email;

}
