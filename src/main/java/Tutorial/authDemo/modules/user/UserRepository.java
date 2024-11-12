package Tutorial.authDemo.modules.user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
  public UserEntity findByEmail(String email);
}
