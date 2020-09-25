package com.milankas.training.userapi.services;

import com.milankas.training.userapi.dto.UserDto;
import com.milankas.training.userapi.dto.in.UserInDto;
import com.milankas.training.userapi.dto.patch.UserPatchDto;
import com.milankas.training.userapi.mappers.PasswordMapper;
import com.milankas.training.userapi.mappers.UserMapper;
import com.milankas.training.userapi.persistance.model.Password;
import com.milankas.training.userapi.persistance.model.User;
import com.milankas.training.userapi.persistance.repository.PasswordRepository;
import com.milankas.training.userapi.persistance.repository.UserRepository;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class UserService {

    private PasswordRepository passwordRepository;
    private UserRepository userRepository;
    private PasswordMapper passwordMapper;
    private UserMapper userMapper;

    public UserService(PasswordRepository passwordRepository, UserRepository userRepository, PasswordMapper passwordMapper, UserMapper userMapper) {
        this.passwordRepository = passwordRepository;
        this.userRepository = userRepository;
        this.passwordMapper = passwordMapper;
        this.userMapper = userMapper;
    }

    public List<UserDto> getUsers() {
        return userMapper.toUsersDto(userRepository.findAll());
    }

    public UserDto getUser(UUID id) {
        return userMapper.userToDto(userRepository.findById(id).orElse(null));
    }

    public UserDto saveUser(UserInDto userInDto) {
        User newUser = new User();
        newUser.setFirstName(userInDto.getFirstName());
        newUser.setLastName(userInDto.getLastName());
        newUser.setEmail(userInDto.getEmail());
        userRepository.save(newUser);
        Password newPassword = new Password();
        newPassword.setStatus(1);
        String salt = DatatypeConverter.printHexBinary(generateSalt()).toLowerCase();
        newPassword.setSalt(salt);
        salt = salt + userInDto.getPassword();
        String hash = generateHash(salt.getBytes(), "SHA-256");
        newPassword.setHash(hash);
        newPassword.setUser(newUser);
        newUser.getPasswords().add(newPassword);
        passwordRepository.save(newPassword);
        userRepository.save(newUser);
        return userMapper.userToDto(newUser);
    }

    public byte[] generateSalt() {
        Random r = new SecureRandom();
        byte[] salt = new byte[16];
        r.nextBytes(salt);
        return salt;
    }

    public String generateHash(byte[] inputBytes, String algorithm) {
        String hashValue = "";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            messageDigest.update(inputBytes);
            byte[] digestedBytes = messageDigest.digest();
            hashValue = DatatypeConverter.printHexBinary(digestedBytes).toLowerCase();
        } catch (Exception e) {

        }
        return hashValue;
    }

    public UserDto deleteUser(UUID userId) {
        User deletedUser = userRepository.findById(userId).orElse(null);
        if (deletedUser == null) {
            return null;
        }
        else {
            userRepository.deleteById(userId);
            return userMapper.userToDto(deletedUser);
        }
    }

    public UserDto updateUser(UUID userId, UserPatchDto userPatchDto) {
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser == null) {
            return null;
        }
        else {
            if (userPatchDto.getFirstName() != null) {
                existingUser.setFirstName(userPatchDto.getFirstName());
            }
            if (userPatchDto.getLastName() != null) {
                existingUser.setLastName(userPatchDto.getLastName());
            }
            if (userPatchDto.getEmail() != null) {
                existingUser.setEmail(userPatchDto.getEmail());
            }
            if (userPatchDto.getPassword() != null) {
                for(int i = 0; i < existingUser.getPasswords().size(); i++) {
                    existingUser.getPasswords().get(i).setStatus(0);
                }
                Password newPassword = new Password();
                newPassword.setStatus(1);
                String salt = DatatypeConverter.printHexBinary(generateSalt()).toLowerCase();
                newPassword.setSalt(salt);
                salt = salt + userPatchDto.getPassword();
                String hash = generateHash(salt.getBytes(), "SHA-256");
                newPassword.setHash(hash);
                existingUser.getPasswords().add(newPassword);
                userRepository.save(existingUser);
            }
        }
        return userMapper.userToDto(userRepository.save(existingUser));
    }
}
