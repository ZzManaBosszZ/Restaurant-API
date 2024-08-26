package com.restaurant.restaurantapi.services.impl;


import com.restaurant.restaurantapi.dtos.UpdateProfileUserRequest;
import com.restaurant.restaurantapi.dtos.UserDTO;
import com.restaurant.restaurantapi.dtos.auth.JwtAuthenticationResponse;
import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.models.auth.*;

public interface AuthenticationService  {

    void signup(SignUpRequest signUpRequest);
    JwtAuthenticationResponse signin(SignInRequest signInRequest);
    JwtAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
    UserDTO profile(User currentUser);
    void changePassword(ChangePasswordRequest changePasswordRequest, User user);
    void forgotPassword(ForgotPasswordRequest forgotPasswordRequest);
    void resetPassword(ResetPasswordRequest resetPasswordRequest, String token);
    void updateProfileUser(UpdateProfileUserRequest updateProfileUserRequest, User currentUser);
    JwtAuthenticationResponse googleSignin(String idToken);
//    JwtAuthenticationResponse facebookSignin(String idToken);
}