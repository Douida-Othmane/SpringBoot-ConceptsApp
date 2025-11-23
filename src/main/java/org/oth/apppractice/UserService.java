package org.oth.apppractice;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.oth.apppractice.Entity.User;
import org.oth.apppractice.DTO.UserDTO;
import org.oth.apppractice.mapper.UserMapper;
import org.oth.apppractice.Exception.BusinessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public UserDTO findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return userMapper.toDto(user);
    }

    public List<UserDTO> getUsers(){
        List<User> users = userRepository.findAll();
        return userMapper.toDtoList(users);
    }

    public void saveUser(UserDTO userDTO){
        User user = userMapper.fromDto(userDTO);
        userRepository.findUserByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new IllegalStateException("Email already in use");
                });
        userRepository.save(user);
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

