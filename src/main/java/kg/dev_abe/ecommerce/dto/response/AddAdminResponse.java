package kg.dev_abe.ecommerce.dto.response;

import kg.dev_abe.ecommerce.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AddAdminResponse {
    private Long id;
    private String email;
    private String password;
    private Role role;
    private String name;
    private String surname;
    private String phoneNumber;
}
