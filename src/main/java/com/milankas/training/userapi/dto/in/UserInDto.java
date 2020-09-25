package com.milankas.training.userapi.dto.in;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

@Getter
@Setter
public class UserInDto {

    private UUID id;

    @NotNull(message = "First name it's required")
    @Size(min = 5, max = 13, message = "First name must be have 5 characters minimum and max 13 characters")
    private String firstName;

    @NotNull(message = "Lastn ame it's required")
    @Size(min = 5, max = 13, message = "Last name must be have 5 characters minimum and max 13 characters")
    private String lastName;

    @NotNull(message = "Email it's required")
    @Email(message = "Must be a valid email address")
    private String email;

    @NotNull(message = "Password it's required")
    @Size(min = 8, max = 15, message = "Password must be have 8 characters minimum and max 15 characters")
    private String password;
}
