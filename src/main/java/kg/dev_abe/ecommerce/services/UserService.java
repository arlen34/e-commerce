package kg.dev_abe.ecommerce.services;

import kg.dev_abe.ecommerce.db.models.User;
import kg.dev_abe.ecommerce.exceptions.ECommerceException;
import kg.dev_abe.ecommerce.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User registerUser(User user) {

        return userRepository.save(user);
    }
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new ECommerceException("User not found"));
    }
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
    public User updateUserId(Long id, User user) {
        user.setId(id);
        userRepository.save(user);
        return userRepository.save(user);
    }

    public void loginUser(User user) {
        // TODO: Add login logic
    }
}
