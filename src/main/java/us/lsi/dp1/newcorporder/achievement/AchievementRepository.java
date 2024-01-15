package us.lsi.dp1.newcorporder.achievement;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AchievementRepository extends CrudRepository<Achievement, Integer> {

    List<Achievement> findByNameContainsIgnoreCase(String name);

}
