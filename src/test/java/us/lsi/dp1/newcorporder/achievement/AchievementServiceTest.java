package us.lsi.dp1.newcorporder.achievement;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import us.lsi.dp1.newcorporder.exception.ResourceNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@MockitoSettings
class AchievementServiceTest {

    @Mock AchievementRepository achievementRepository;
    @InjectMocks AchievementService achievementService;

    @Test
    void givenExistingAchievementId_whenGettingById_returnsAchievement() {
        Achievement achievement = Achievement.builder().id(1).build();
        when(achievementRepository.findById(1)).thenReturn(Optional.of(achievement));

        assertThat(achievementService.findById(1)).isEqualTo(achievement);
    }

    @Test
    void givenNonExistingAchievementId_whenGettingById_throwsResourceNotFoundException() {
        assertThatThrownBy(() -> achievementService.findById(2)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void givenAchievement_whenUpdating_updatesEntityAndSaves() {
        Achievement original = Achievement.builder()
            .id(1)
            .stat(Stat.GAMES_TIED)
            .build();
        when(achievementRepository.findById(1)).thenReturn(Optional.of(original));

        Achievement updateData = Achievement.builder()
            .stat(Stat.GAMES_WON)
            .imageUrl("imageUrl")
            .build();

        Achievement updated = achievementService.updateAchievement(updateData, 1);

        assertThat(updated.getId()).isEqualTo(1);
        assertThat(updated.getStat()).isEqualTo(Stat.GAMES_WON);
        assertThat(updated.getImageUrl()).isEqualTo("imageUrl");
        verify(achievementRepository).save(updated);
    }

    @Test
    void givenAchievementId_whenDeleting_deletesFromRepository() {
        Achievement achievement = Achievement.builder().id(1).build();
        when(achievementRepository.findById(1)).thenReturn(Optional.of(achievement));

        achievementService.deleteAchievement(1);

        verify(achievementRepository).delete(achievement);
    }
}
