package com.milankas.training.userapi.dto.patch;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
public class UserPatchDto {

    private UUID id;

    @Size(min = 5, max = 13, message = "First name must be have 5 characters minimum and max 13 characters")
    private String firstName;

    @Size(min = 5, max = 13, message = "Last name must be have 5 characters minimum and max 13 characters")
    private String lastName;

    @Email(message = "Must be a valid email address")
    private String email;

    @Size(min = 8, max = 15, message = "Password must be have 8 characters minimum and max 15 characters")
    private String password;
}
