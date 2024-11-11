package Tutorial.authDemo.modules.user;


import Tutorial.authDemo.shared.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, Long> {

  @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
  public UserEntity findByEmail(String email);
}
