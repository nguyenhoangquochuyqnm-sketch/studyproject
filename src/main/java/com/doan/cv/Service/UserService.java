package com.doan.cv.Service;

import com.doan.cv.Entity.User;
import com.doan.cv.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getALlUsers(){
        return userRepository.findAll();
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) {
        User exsistingUser = userRepository
                             .findById(id)
                             .orElseThrow(() -> new RuntimeException("User not found "+id));
        exsistingUser.setName(user.getName());
        exsistingUser.setPassword(user.getPassword());

        return userRepository.save(exsistingUser);
    }

    public void deleteUser(Long id) {
        if(!userRepository.existsById(id))
            throw new RuntimeException("USER NOT FOUND");

        userRepository.deleteById(id);
    }

    public User getUserById(Long id) {
        return userRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("User not found "+id));
    }
}
