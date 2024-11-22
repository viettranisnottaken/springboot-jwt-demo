package Tutorial.authDemo.modules.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "USERS")
@Data
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@Accessors
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Column(name = "id", nullable = false)
  private Long id;

  private String email;
  private String hashedPassword;
  private String firstName;
  private String lastName;
}
