package com.restaurant.restaurantapi.controllers;


import com.restaurant.restaurantapi.dtos.ResponseObject;
import com.restaurant.restaurantapi.dtos.auth.JwtAuthenticationResponse;
import com.restaurant.restaurantapi.dtos.UserDTO;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import com.restaurant.restaurantapi.models.auth.*;
import com.restaurant.restaurantapi.services.IAuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthenticationController {

        private final IAuthenticationService authenticationService;

        @PostMapping("/auth/user/signup")
        public ResponseEntity<User> signup(@RequestBody SignUpRequest signUpRequest) {
            return ResponseEntity.ok(authenticationService.signup(signUpRequest));
        }

        @PostMapping("/auth/user/signip")
        public ResponseEntity<JwtAuthenticationResponse> signip(@RequestBody SignInRequest signInRequest) {
            return ResponseEntity.ok(authenticationService.signin(signInRequest));
        }

        @PostMapping("/auth/user/refresh")
        public ResponseEntity<JwtAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest) {
            return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
        }


        @GetMapping("/user/profile")
        ResponseEntity<ResponseObject> profile() {
            Authentication auth = SecurityContextHolder.getContext()
                    .getAuthentication();
            if (!(auth.getPrincipal() instanceof User)) {
                throw new AppException(ErrorCode.NOTFOUND);
            }
            if (auth == null) throw new AppException(ErrorCode.UNAUTHENTICATED);
            User currentUser = (User) auth.getPrincipal();
            UserDTO userDTO = authenticationService.profile(currentUser);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(true, 200, "ok", userDTO)
            );
        }
    @PostMapping("/user/change-password")
    public ResponseEntity<ResponseObject> changepassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        Authentication auth = SecurityContextHolder.getContext()
                .getAuthentication();
        if (!(auth.getPrincipal() instanceof User)) {
            throw new AppException(ErrorCode.NOTFOUND);
        }
        if (auth == null) throw new AppException(ErrorCode.UNAUTHENTICATED);
        User currentUser = (User) auth.getPrincipal();
        authenticationService.changePassword(changePasswordRequest, currentUser);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }

    @PostMapping("/auth/user/forgot-password")
    public ResponseEntity<ResponseObject> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        authenticationService.forgotPassword(forgotPasswordRequest);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }

    @PostMapping("/auth/user/reset-password/{token}")
    public ResponseEntity<ResponseObject> resetPassword(
            @PathVariable("token") String token,
            @RequestBody ResetPasswordRequest resetPasswordRequest
    ) {
        authenticationService.resetPassword(resetPasswordRequest, token);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(true, 200, "ok", "")
        );
    }
    }

