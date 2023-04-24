package kg.dev_abe.ecommerce.dto.response;

import kg.dev_abe.ecommerce.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private Role role;
}
