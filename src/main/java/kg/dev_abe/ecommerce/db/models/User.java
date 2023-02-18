package kg.dev_abe.ecommerce.db.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import kg.dev_abe.ecommerce.db.models.enums.Role;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User {
    private static final String SEQ_NAME = "user_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1, initialValue = 3)
    private Long id;
    @Email
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String nickname;
    @Min(8)
    private String password;

    private String phoneNumber;
    @Enumerated(EnumType.STRING)
    private Role role;

}
