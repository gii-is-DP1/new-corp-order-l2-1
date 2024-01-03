package us.lsi.dp1.newcorporder.achievement;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.lsi.dp1.newcorporder.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AchievementService {

    private final AchievementRepository achievementRepository;

    public AchievementService(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    @Transactional(readOnly = true)
    public Iterable<Achievement> findAll() {
        return this.achievementRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Achievement findById(Integer id) {
        return this.achievementRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Achievement", "id", id));
    }

    @Transactional
    public Achievement save(@Valid Achievement achievement) throws DataAccessException {
        return achievementRepository.save(achievement);
    }

    @Transactional
    public Achievement updateAchievement(@Valid Achievement achievement, Integer idToUpdate) {
        Achievement toUpdate = findById(idToUpdate);
        BeanUtils.copyProperties(achievement, toUpdate, "id");
        save(toUpdate);
        return toUpdate;
    }

    @Transactional
    public void deleteAchievement(Integer id) {
        Achievement toDelete = findById(id);
        achievementRepository.delete(toDelete);
    }

    @Transactional
    public List<Achievement> getAllFilteredAchievements(String name) {
        List<Achievement> achievements = this.achievementRepository.findAll();
        if (name == null || name.isEmpty()) {
            return achievements;
        }

        return achievements.stream()
            .filter(achievement -> achievement.getName().toLowerCase().contains(name.toLowerCase()))
            .collect(Collectors.toList());
    }

}
