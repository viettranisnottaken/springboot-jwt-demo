package Tutorial.authDemo.modules.user;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  UserEntity findByEmail(String email);

  @Query("select u from UserEntity u where lower(u.firstName) like lower(concat('%', ?1, '%')) or lower(u.lastName) like lower(concat('%', ?1, '%'))")
  Page<UserEntity> findByName(String name, Pageable pageable);
}
