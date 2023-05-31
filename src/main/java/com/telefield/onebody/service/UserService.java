package com.telefield.onebody.service;

import com.telefield.onebody.dto.LoginDto;
import com.telefield.onebody.dto.UserDto;
import com.telefield.onebody.dto.UserInfoDto;
import com.telefield.onebody.entity.Gateway;
import com.telefield.onebody.entity.User;
import com.telefield.onebody.entity.UserInfo;
import com.telefield.onebody.exception.DeviceException;
import com.telefield.onebody.exception.UserException;
import com.telefield.onebody.repository.GatewayRepository;
import com.telefield.onebody.repository.UserInfoRepository;
import com.telefield.onebody.repository.UserRepository;
import com.telefield.onebody.type.GenderType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static com.telefield.onebody.exception.DeviceErrorCode.NO_DEVICE;
import static com.telefield.onebody.exception.UserErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GatewayRepository gatewayRepository;
    private final UserInfoRepository userInfoRepository;

    @Transactional
    public User SignUp(UserDto.Request request) {
        log.info(request.toString());
        Optional<User> byUserId = userRepository.findByUserId(request.getUserId());
        if (!byUserId.isPresent()) {

            User user = User.builder()
                    .userId(request.getUserId())
                    .password(request.getPassword())
                    .userName(request.getUsername())
                    .location(request.getLocation())
                    .phoneNumber(request.getPhoneNumber())
                    .allowed(false)
                    .build();

            userRepository.save(user);

            return user;
        } else throw new UserException(ALREADY_REGISTERED_USER);
    }

    @Transactional
    public void allowSignUp(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UserException(NO_USER));
        if (user.isAllowed()) throw new UserException(ALREADY_ALLOWED_USER);

        user.setAllowed(true);
    }

    public boolean Login(LoginDto loginDto) {
        log.info(loginDto.toString());
        User user = userRepository.findByUserIdAndPassword(loginDto.getUserId(), loginDto.getPassword())
                .orElseThrow(() -> new UserException(NO_USER));
        return user.isAllowed();
    }

    @Transactional
    public UserInfo updateUserInfo(String macAddress, UserInfoDto.Request request) {
        Gateway gateway = gatewayRepository.findByGwMacAddress(macAddress)
                .orElseThrow(
                        () -> new DeviceException(NO_DEVICE)
                );
        UserInfo userInfo = userInfoRepository.findByGwMacAddress(macAddress).orElseThrow(
                () -> new DeviceException(NO_DEVICE)
        );

        gateway.setUserName(request.getUsername());
        gateway.setLocation(request.getLocation());

        userInfo.setUserName(request.getUsername());
        switch (request.getGender()) {
            case "남자":
                userInfo.setGender(GenderType.MAN);
                break;
            case "여자":
                userInfo.setGender(GenderType.WOMAN);
                break;
        }
        userInfo.setLocation(request.getLocation());
        userInfo.setBirthDate(request.getBirthdate());

        return userInfo;
    }

    public UserInfo getUserInfo(String macAddress) {
        return userInfoRepository.findByGwMacAddress(macAddress).orElseThrow(
                () -> new DeviceException(NO_DEVICE)
        );
    }
}
