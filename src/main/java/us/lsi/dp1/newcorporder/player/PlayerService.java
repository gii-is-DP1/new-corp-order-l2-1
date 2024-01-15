package us.lsi.dp1.newcorporder.player;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import us.lsi.dp1.newcorporder.auth.ApplicationUserDetails;
import us.lsi.dp1.newcorporder.exception.ResourceNotFoundException;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Iterable<Player> findAll() {
        return this.playerRepository.findAll();
    }

    public Player findById(Integer id) {
        return this.playerRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Player", "id", id));
    }

    @Transactional(readOnly = true)
    public Player findByUserId(Integer id) {
        return this.playerRepository
            .findByUserId(id)
            .orElseThrow(() -> new ResourceNotFoundException("Player", "id", id));
    }


    public Player findByUsername(String username) {
        return this.playerRepository.findByUserUsernameIgnoreCase(username)
            .orElseThrow(() -> new ResourceNotFoundException("Player", "username", username));
    }

    @Transactional
    public Player save(@Valid Player player) throws DataAccessException {
        return playerRepository.save(player);
    }

    @Transactional
    public Player updatePlayer(@Valid Player player, Integer idToUpdate) {
        Player toUpdate = findById(idToUpdate);
        BeanUtils.copyProperties(player, toUpdate, "id");
        playerRepository.save(toUpdate);
        return toUpdate;
    }

    @Transactional
    public void deletePlayer(Integer id) {
        Player toDelete = findById(id);
        playerRepository.delete(toDelete);
    }

    @Transactional
    public Player getAuthenticatedPlayer() {
        ApplicationUserDetails userDetails = (ApplicationUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return this.findById(userDetails.getId());
    }
}
