package kg.dev_abe.ecommerce.services;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import jakarta.annotation.PostConstruct;
import kg.dev_abe.ecommerce.dto.request.AddAdminRequest;
import kg.dev_abe.ecommerce.dto.request.LoginRequest;
import kg.dev_abe.ecommerce.dto.request.RegisterRequest;
import kg.dev_abe.ecommerce.dto.response.AddAdminResponse;
import kg.dev_abe.ecommerce.dto.response.AuthResponse;
import kg.dev_abe.ecommerce.dto.response.UserResponse;
import kg.dev_abe.ecommerce.exceptions.BadRequestException;
import kg.dev_abe.ecommerce.exceptions.ECommerceException;
import kg.dev_abe.ecommerce.exceptions.NotFoundException;
import kg.dev_abe.ecommerce.mappers.UserMapper;
import kg.dev_abe.ecommerce.models.User;
import kg.dev_abe.ecommerce.models.enums.Role;
import kg.dev_abe.ecommerce.repositories.UserRepository;
import kg.dev_abe.ecommerce.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager manager;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() throws IOException {
        GoogleCredentials googleCredentials =
                GoogleCredentials.fromStream(new ClassPathResource("serviceAccountKey.json")
                        .getInputStream());
        FirebaseOptions firebaseOptions = FirebaseOptions.builder()
                .setCredentials(googleCredentials).build();
        FirebaseApp.initializeApp(firebaseOptions);
    }

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

            User user = new User(request);
            user.setPassword(passwordEncoder.encode(request.getPassword1()));
            user.setRole(Role.USER);
            userRepository.save(user);
            String token = jwtUtils.generateToken(user.getEmail());

            return new AuthResponse(user.getEmail(), token, user.getRole());
        } else throw new BadRequestException("The passwords not matches");
        //I need to look again
    }

    public UserResponse findUserById(Long id) {
        return userRepository.findById(id).map(userMapper::toUserResponse).orElseThrow(NotFoundException::new);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new ECommerceException("User not found"));
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUserId(Long id, User user) {
        user.setId(id);
        userRepository.save(user);
        return userRepository.save(user);
    }

    public AddAdminResponse addAdmin(AddAdminRequest request) {
        if (userRepository.existsUserByEmail(request.getEmail())) {
            throw new BadRequestException("This email: " + request.getEmail() + " is always use!");
        }
        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .surname(request.getSurname())
                .name(request.getName())
                .role(Role.ADMIN)
                .build();
        userRepository.save(user);
        return new AddAdminResponse(user.getId(), user.getEmail(), request.getPassword(), user.getRole(),
                user.getName(), user.getSurname(), user.getPhoneNumber());
    }

    public List<UserResponse> getUsers() {
        return userRepository.findUsers().stream().map(userMapper::toUserResponse).toList();
    }

    public AuthResponse authWithGoogleAccount(String tokenId) throws FirebaseAuthException {
        FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(tokenId);
        User user;
        if (!userRepository.existsUserByEmail(firebaseToken.getEmail())) {
            User newUser = new User();
            String[] name = firebaseToken.getName().split(" ");
            newUser.setName(name[0]);
            newUser.setSurname(name[1]);
            newUser.setRole(Role.USER);
            userRepository.save(newUser);
        }
        user = userRepository.findUserByEmail(firebaseToken.getEmail());
        return new AuthResponse(user.getEmail(),
                jwtUtils.generateToken(user.getEmail()),
                user.getRole());
    }
}
