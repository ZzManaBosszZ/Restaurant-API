package com.restaurant.restaurantapi.services.impl;

import com.restaurant.restaurantapi.dtos.UserDTO;
import com.restaurant.restaurantapi.dtos.auth.JwtAuthenticationResponse;
import com.restaurant.restaurantapi.entities.Role;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import com.restaurant.restaurantapi.models.auth.*;
import com.restaurant.restaurantapi.models.mail.MailStructure;
import com.restaurant.restaurantapi.repositories.UserRepository;
import com.restaurant.restaurantapi.services.IAuthenticationService;
import com.restaurant.restaurantapi.services.IJWTService;
import com.restaurant.restaurantapi.services.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements IAuthenticationService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final IJWTService jwtService;
    private final MailService mailService;
    private static final String ALLOWED_CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";


    public static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            sb.append(ALLOWED_CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

    @Override
    public User signup(SignUpRequest signUpRequest) {
        User user = new User();
//        user.setCode(generateRandomString(8));
        user.setFullName(signUpRequest.getFullname());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public JwtAuthenticationResponse signin(SignInRequest signInRequest) {
        var user = userRepository.findByEmail(signInRequest.getEmail()).orElseThrow(() -> new AppException(ErrorCode.INVALIDEMAILORPASSWORD));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
                signInRequest.getPassword()));

        var jwt = jwtService.generateToken(user);

        var refreshToken = jwtService.generateRefreshToken(new HashMap<>(), user);

        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

        jwtAuthenticationResponse.setToken(jwt);
        jwtAuthenticationResponse.setRefreshToken(refreshToken);
        return jwtAuthenticationResponse;
    }

    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String useEmail = jwtService.extractUsername(refreshTokenRequest.getToken());
        User user = userRepository.findByEmail(useEmail).orElseThrow();
        if (jwtService.isTokenValid(refreshTokenRequest.getToken(), user)){
            var jwt = jwtService.generateToken(user);

            JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();

            jwtAuthenticationResponse.setToken(jwt);
            jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
            return jwtAuthenticationResponse;
        }
        return null;
    }

    @Override
    public UserDTO profile(User currentUser) {
        if (currentUser == null) throw new AppException(ErrorCode.NOTFOUND);
        UserDTO userDTO = UserDTO.builder()
                .id(currentUser.getId())
//                .code(currentUser.getCode())
                .fullName(currentUser.getFullName())
                .email(currentUser.getEmail())
                .phone(currentUser.getPhone())
                .birthday(currentUser.getBirthDay())
                .address(currentUser.getAddress())
                .status(currentUser.getStatus())
                .build();
        return userDTO;
    }




    @Override
    public void changePassword(ChangePasswordRequest changePasswordRequest, User user) {
        System.out.println(changePasswordRequest.toString());
        if (!passwordEncoder.matches(changePasswordRequest.getCurrentPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect current password");
        }
        if (!changePasswordRequest.getNewPassword().equals(changePasswordRequest.getConfirmPassword())){
            throw new RuntimeException("Incorrect current password");
        }
        user.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    public void forgotPassword(ForgotPasswordRequest forgotPasswordRequest) {
        User user = userRepository.findByEmail(forgotPasswordRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));

        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR_OF_DAY, 1);


        String resetToken = generateResetToken();
        user.setResetToken(resetToken);
        user.setResetTokenExpiry((java.sql.Date) calendar.getTime());
        userRepository.save(user);

        String resetLink = "http://localhost:3001/reset-password/" + resetToken;

        MailStructure mailStructure = new MailStructure();
        mailStructure.setSubject("Password Reset");
        mailStructure.setMessage("Click the link to reset your password: " + resetLink);

        mailService.sendMail(forgotPasswordRequest.getEmail(), mailStructure);
    }

    @Override
    public void resetPassword(ResetPasswordRequest resetPasswordRequest, String token) {
        User user = userRepository.findByEmail(resetPasswordRequest.getEmail())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOTFOUND));

        Date now = new Date();

        if (token == null || !user.getResetToken().equals(token) || !user.getEmail().equals(resetPasswordRequest.getEmail()) || user.getResetTokenExpiry().before(now))
        {
            throw new AppException(ErrorCode.INVALID_RESETTOKEN);
        }

        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getNewPassword()));
        user.setResetToken(null);
        user.setResetTokenExpiry(null);
        userRepository.save(user);
    }



    private String generateResetToken() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}