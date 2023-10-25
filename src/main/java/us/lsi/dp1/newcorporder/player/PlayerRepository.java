package us.lsi.dp1.newcorporder.player;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import us.lsi.dp1.newcorporder.user.*;

public interface PlayerRepository extends CrudRepository<Player, Integer>{

    @Query("SELECT p FROM Player p")
    Page<Player> findAllPlayers(Pageable pageable);



    @Query("SELECT p FROM Player p WHERE p.user.username LIKE :username")
    Player findPlayerByUsername(@Param("username") String username);
}
