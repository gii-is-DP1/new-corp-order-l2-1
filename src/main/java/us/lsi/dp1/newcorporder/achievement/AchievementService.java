package us.lsi.dp1.newcorporder.achievement;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.lsi.dp1.newcorporder.exceptions.ResourceNotFoundException;

@Service
public class AchievementService {
    private final AchievementRepository achievementRepository;
    @Autowired
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

    @Transactional(readOnly = true)
    public Achievement add(Achievement achievement) {
        return this.achievementRepository.save(achievement);
    }

    @Transactional
    public Achievement save(@Valid Achievement achievement) throws DataAccessException {
        achievementRepository.save(achievement);
        return achievement;
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
}
