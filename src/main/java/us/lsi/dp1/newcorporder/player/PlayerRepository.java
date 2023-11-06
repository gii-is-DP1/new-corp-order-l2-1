package us.lsi.dp1.newcorporder.player;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
    Page<Player> findAllPlayers(Pageable pageable);

    Player findPlayerByUsername(@Param("username") String username);
}
