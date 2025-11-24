package org.oth.apppractice.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.oth.apppractice.Entity.User;
import org.oth.apppractice.dto.RegistrationRequestDto;
import org.oth.apppractice.dto.UserDto;
import org.oth.apppractice.Repository.UserRepository;
import org.oth.apppractice.mapper.UserMapper;
import org.oth.apppractice.Exception.BusinessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return userMapper.UsertoUserDto(user);
    }

    public List<UserDto> getUsers(){
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }

    public void saveUser(UserDto userDto){
        User user = userMapper.UserDtotoUser(userDto);
        checkEmail(user.getEmail());
        userRepository.save(user);
    }

    public void registerUser(RegistrationRequestDto registrationDto){
        User user = userMapper.UserFromRegistrationDto(registrationDto);
        checkEmail(user.getEmail());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    private void checkEmail(String email){
        userRepository.findUserByEmail(email)
                .ifPresent(u -> {
                    throw new IllegalStateException("Email already in use");
                });
    }

    public void deleteUser(Long userId){
        boolean exists = userRepository.existsById(userId);
        if(!exists){
            throw new IllegalStateException("User with id " + userId + " not found");
        }
        userRepository.deleteById(userId);
    }

    @Transactional
    public void updateUser(Long userId, String name, String email){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException(
                        "User with id " + userId + " not found"));
        if(name != null && !name.isEmpty() && !Objects.equals(user.getName(), name)){
            user.setName(name);
        }
        if(email != null && !email.isEmpty() && !Objects.equals(user.getEmail(), email)){
            userRepository.findUserByEmail(email)
                    .ifPresent(u -> {
                        throw new IllegalStateException("Email already in use");
                    });
            user.setEmail(email);
        }
        userRepository.save(user);
    }

    public void updateEmailTransactional(Long userId, String email) throws BusinessException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with id " + userId + " not found"));
        if(email == null || email.isBlank()){
            throw new BusinessException("Email cannot be empty");
        }

        if (email.endsWith("@blocked.com")) {
            throw new BusinessException("This domain is forbidden");
        }

        user.setEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() ->
                    new UsernameNotFoundException(
                            String.format("user with email %s not found", email)));
    }
}

