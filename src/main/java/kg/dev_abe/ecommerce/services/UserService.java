package kg.dev_abe.ecommerce.services;

import jakarta.transaction.Transactional;
import kg.dev_abe.ecommerce.dto.request.LoginRequest;
import kg.dev_abe.ecommerce.dto.request.RegisterRequest;
import kg.dev_abe.ecommerce.dto.response.AuthResponse;
import kg.dev_abe.ecommerce.exceptions.BadRequestException;
import kg.dev_abe.ecommerce.models.User;
import kg.dev_abe.ecommerce.exceptions.ECommerceException;
import kg.dev_abe.ecommerce.repositories.UserRepository;
import kg.dev_abe.ecommerce.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager manager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(LoginRequest request) {
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(authentication.getName()).orElseThrow(() -> new BadCredentialsException("Password or email not found"));
        String token = jwtUtils.generateToken(user.getEmail());
        return new AuthResponse(user.getEmail(), token, user.getRole());
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw new BadRequestException("This email: " + request.getEmail() + " is always use!");
        }
        if (request.getPassword1().equals(request.getPassword2())) {
            request.setPassword1(passwordEncoder.encode(request.getPassword1()));
            User saveUser = userRepository.save(new User(request));
            String token = jwtUtils.generateToken(saveUser.getEmail());
            return new AuthResponse(saveUser.getEmail(), token, saveUser.getRole());
        } else throw new BadRequestException("The passwords not matches");
        //I need to look again
    }

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

}
