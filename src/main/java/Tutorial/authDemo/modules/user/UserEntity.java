package Tutorial.authDemo.modules.user;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "USERS")
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Accessors(fluent = true)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    private String email;
    private String hashedPassword;
}
