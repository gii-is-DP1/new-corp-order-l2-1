package us.lsi.dp1.newcorporder.achievement;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import us.lsi.dp1.newcorporder.user.User;

import java.util.List;
import java.util.Optional;

public interface AchievementRepository extends CrudRepository<Achievement, Integer> {
    @Override
    List<Achievement> findAll();

    @Override
    Optional<Achievement> findById(Integer integer);
}
