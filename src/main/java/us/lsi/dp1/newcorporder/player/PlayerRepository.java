package us.lsi.dp1.newcorporder.player;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlayerRepository extends CrudRepository<Player, Integer> {

    Optional<Player> findByUserId(Integer integer);

    Optional<Player> findByUserUsernameIgnoreCase(String username);

}
