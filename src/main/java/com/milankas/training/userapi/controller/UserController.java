package com.milankas.training.userapi.controller;

import com.milankas.training.userapi.dto.UserDto;
import com.milankas.training.userapi.dto.in.UserInDto;
import com.milankas.training.userapi.dto.patch.UserPatchDto;
import com.milankas.training.userapi.errors.ErrorResponse;
import com.milankas.training.userapi.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/v1/users")
    public List<UserDto> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/v1/users/{userId}")
    public UserDto getUserById(@PathVariable UUID userId){
        if (userService.getUser(userId) == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "user not found"
            );
        }
        else {
            return userService.getUser(userId);
        }
    }

    @PostMapping("/v1/users")
    public ResponseEntity<Object> postUser(@Valid @RequestBody UserInDto userInDto, BindingResult result){
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponse("400", "Validation Failure", errors));
        }
        else {
            return ResponseEntity.ok(userService.saveUser(userInDto));
        }
    }

    @DeleteMapping("/v1/users/{userId}")
    public ResponseEntity deleteUser(@PathVariable UUID userId) {
        if (userService.deleteUser(userId) == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "user not found"
            );
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
    }

    @PatchMapping("/v1/users/{userId}")
    public ResponseEntity<Object> patchUser(@PathVariable UUID userId,@Valid @RequestBody UserPatchDto userPatchDto, BindingResult result) {
        if (result.hasErrors()) {
            List<String> errors = result.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
            return ResponseEntity.badRequest().body(new ErrorResponse("400", "Validation Failure", errors));
        }
        else {
            if (userService.getUser(userId) == null) {
                return ResponseEntity.notFound().build();
            }
            else {
                return ResponseEntity.ok(userService.updateUser(userId, userPatchDto));
            }
        }
    }
}
