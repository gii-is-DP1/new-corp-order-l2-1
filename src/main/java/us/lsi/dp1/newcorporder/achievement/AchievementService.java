package us.lsi.dp1.newcorporder.achievement;

import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.lsi.dp1.newcorporder.exception.ResourceNotFoundException;
import us.lsi.dp1.newcorporder.player.Player;
import us.lsi.dp1.newcorporder.stats.player.PlayerMatchStatsService;

import java.util.ArrayList;
import java.util.List;

@Service
public class AchievementService {

    private final PlayerMatchStatsService playerMatchStatsService;
    private final AchievementRepository achievementRepository;

    public AchievementService(AchievementRepository achievementRepository, PlayerMatchStatsService playerMatchStatsService) {
        this.achievementRepository = achievementRepository;
        this.playerMatchStatsService = playerMatchStatsService;
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
        return this.achievementRepository.findByNameContainsIgnoreCase(name);
    }

    @Transactional
    public List<Achievement> getAllAchievementsByPlayer(Player player) {
        List<Achievement> allAchievements = (List<Achievement>) this.achievementRepository.findAll();
        List<Achievement> achievementsCompleted = new ArrayList<>();
        for (Achievement achievement : allAchievements) {
            if(isAchievementCompleted(achievement, player))
                achievementsCompleted.add(achievement);
        }
        return achievementsCompleted;
    }

    @Transactional
    public boolean isAchievementCompleted(Achievement achievement, Player player) {
        return achievement.getThreshold() <= switch (achievement.getStat()) {
            case GAMES_LOST -> playerMatchStatsService.getStats(player).getLoses();
            case GAMES_TIED -> playerMatchStatsService.getStats(player).getTies();
            case GAMES_WON -> playerMatchStatsService.getStats(player).getWins();
            case TIMES_PLAYED -> playerMatchStatsService.getStats(player).getTotalMatches();
            case TIMES_PLOTTED-> playerMatchStatsService.getTimesPlottedByPlayerId(player.getId());
            case TIMES_INFILTRATED-> playerMatchStatsService.getTimesInfiltratedByPlayerId(player.getId());
            case TIMES_TAKEN_OVER-> playerMatchStatsService.getTimesTakenOverByPlayerId(player.getId());
            case CONSULTANT_STATS_USED-> playerMatchStatsService.getConsultantUsedByPlayerId(player.getId());
            case ABILITIES_USED->playerMatchStatsService.getAbilityUsedByPlayerId(player.getId());
            case FINAL_SCORE -> playerMatchStatsService.getMaxFinalScoreByPlayerId(player.getId());
        };
    }
}
