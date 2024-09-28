package com.restaurant.restaurantapi.services.impl;

import com.restaurant.restaurantapi.entities.User;
import com.restaurant.restaurantapi.exceptions.AppException;
import com.restaurant.restaurantapi.exceptions.ErrorCode;
import com.restaurant.restaurantapi.repositories.UserRepository;
import com.restaurant.restaurantapi.services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Optional<User> userOptional = userRepository.findByEmail(username);
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    if ("user".equals(user.getUserType())) {
                        return userOptional.get();
                    }
                }

                throw new AppException(ErrorCode.INVALIDEMAILORPASSWORD);
            }
        };
    }
}
