package kg.dev_abe.ecommerce.db.models;

import jakarta.persistence.*;
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
    private String name;
    private String surname;
    private String nickname;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;

}
