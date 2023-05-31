package com.telefield.onebody.controller;

import com.telefield.onebody.dto.LoginDto;
import com.telefield.onebody.dto.UserDto;
import com.telefield.onebody.dto.UserInfoDto;
import com.telefield.onebody.entity.User;
import com.telefield.onebody.entity.UserInfo;
import com.telefield.onebody.exception.UserErrorCode;
import com.telefield.onebody.exception.UserException;
import com.telefield.onebody.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        boolean login = userService.Login(loginDto);
        if (login)
            return new ResponseEntity<>(HttpStatus.OK);
        else throw new UserException(UserErrorCode.NOT_ALLOWED_USER);
    }

    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody UserDto.Request userDto) {
        User user = userService.SignUp(userDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/signUp/allow/{userId}")
    public ResponseEntity<?> AllowSignUp(@PathVariable String userId) {
        userService.allowSignUp(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/user/info/{macAddress}")
    public UserInfo getUserInfo(@PathVariable String macAddress) {
        return userService.getUserInfo(macAddress);
    }

    @PutMapping("/update/user/info/{macAddress}")
    public ResponseEntity<?> UpdateUserInfo(@PathVariable String macAddress, @RequestBody UserInfoDto.Request request) {
        log.info("/update/user/info/{}", macAddress);
        userService.updateUserInfo(macAddress, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
