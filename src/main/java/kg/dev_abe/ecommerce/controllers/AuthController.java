package kg.dev_abe.ecommerce.controllers;

import kg.dev_abe.ecommerce.db.models.User;
import kg.dev_abe.ecommerce.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class AuthController {

    private UserService userService;
    @GetMapping("{id}")
    public ResponseEntity <User> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.findUserById(id), HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity <String> registerUser(@RequestBody User user) {
        userService.registerUser(user);
        return new ResponseEntity<>("User registration successful", HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity <String> loginUser(@RequestBody User user){
        userService.loginUser(user);
        return new ResponseEntity<>("User login successful", HttpStatus.OK);
    }
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
