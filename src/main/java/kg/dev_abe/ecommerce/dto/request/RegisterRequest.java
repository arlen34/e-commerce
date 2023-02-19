package kg.dev_abe.ecommerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String password1;
    @NotBlank
    private String password2;
}
