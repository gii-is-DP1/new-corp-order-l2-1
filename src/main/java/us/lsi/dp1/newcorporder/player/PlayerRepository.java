package us.lsi.dp1.newcorporder.player;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import us.lsi.dp1.newcorporder.user.User;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
    @Override
    List<Player> findAll();

    @Override
    Optional<Player> findById(Integer integer);


}
