package Tutorial.authDemo.modules.user;

import Tutorial.authDemo.shared.repository.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<UserEntity, Long> {

}
