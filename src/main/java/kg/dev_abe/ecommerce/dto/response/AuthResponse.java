package kg.dev_abe.ecommerce.dto.response;

import kg.dev_abe.ecommerce.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {

    private String nickname;
    private String token;
    private Role role;
}
