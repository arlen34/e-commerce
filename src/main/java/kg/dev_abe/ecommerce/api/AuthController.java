package kg.dev_abe.ecommerce.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kg.dev_abe.ecommerce.dto.request.LoginRequest;
import kg.dev_abe.ecommerce.dto.request.RegisterRequest;
import kg.dev_abe.ecommerce.dto.response.AuthResponse;
import kg.dev_abe.ecommerce.models.User;
import kg.dev_abe.ecommerce.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
@Tag(name = "Authentication API",description = "The authentication API (for authentication)")
public class AuthController {

    private UserService userService;

    @Operation(summary = "Retrieve Authentication Token",
            description = "This endpoint returns a JWT for authenticating further requests to the API")
    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest request){
        return userService.login(request);
    }

    @Operation(summary = "Registration", description = "The endpoint for register user")
    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterRequest request){
        return userService.register(request);
    }



    @GetMapping("{id}")
    public ResponseEntity <User> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }
//    @PostMapping("/register")
//    public ResponseEntity <String> registerUser(@RequestBody User user) {
//        userService.registerUser(user);
//        return new ResponseEntity<>("User registration successful", HttpStatus.OK);
//    }
//    @PostMapping("/login")
//    public ResponseEntity <String> loginUser(@RequestBody User user){
//        userService.loginUser(user);
//        return new ResponseEntity<>("User login successful", HttpStatus.OK);
//    }
    @DeleteMapping("{id}")
    public ResponseEntity <String> deleteUserById(@PathVariable Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
    }
    @PutMapping("{id}")
    public ResponseEntity <User> updateUserId(@PathVariable Long id, @RequestBody User user) {
        return new ResponseEntity<>(userService.updateUserId(id, user), HttpStatus.OK);
    }

}
