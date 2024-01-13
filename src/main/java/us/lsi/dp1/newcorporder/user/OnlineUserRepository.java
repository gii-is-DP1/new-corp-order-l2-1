package us.lsi.dp1.newcorporder.user;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.Optional;

@Repository
public class OnlineUserRepository {

    private static final Duration MAX_DIFFERENCE = Duration.ofSeconds(10);

    private final Cache<Integer, Instant> lastPresences = CacheBuilder.newBuilder()
        .expireAfterWrite(MAX_DIFFERENCE)
        .build();

    public void updateLastPresence(User user) {
        lastPresences.put(user.getId(), Instant.now());
    }

    public boolean isOnline(User user) {
        return Optional.ofNullable(lastPresences.getIfPresent(user.getId()))
            .map(lastPresence -> Duration.between(lastPresence, Instant.now()).minus(MAX_DIFFERENCE).isNegative())
            .orElse(false);
    }
}
