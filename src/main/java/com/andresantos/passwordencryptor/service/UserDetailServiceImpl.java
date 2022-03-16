package com.andresantos.passwordencryptor.service;

import com.andresantos.passwordencryptor.data.UserDetailData;
import com.andresantos.passwordencryptor.model.UserModel;
import com.andresantos.passwordencryptor.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> userModel = repository.findByLogin(username);

        if(userModel.isEmpty()) {
            throw new UsernameNotFoundException("User [" + username + "] not found.");
        }

        return new UserDetailData(userModel);
    }
}
