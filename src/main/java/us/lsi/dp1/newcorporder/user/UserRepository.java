package us.lsi.dp1.newcorporder.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findById(Integer id);

    Optional<User> findByUsernameIgnoreCase(String username);

    boolean existsByUsernameIgnoreCase(String username);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.authority.name = :auth")
    Iterable<User> findAllByAuthority(String auth);

}
