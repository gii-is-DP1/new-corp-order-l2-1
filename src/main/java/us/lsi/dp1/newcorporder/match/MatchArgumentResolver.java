package us.lsi.dp1.newcorporder.match;

import jakarta.annotation.Nonnull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MatchArgumentResolver implements Converter<String, Match> {

    private final MatchService matchService;

    public MatchArgumentResolver(Optional<MatchService> matchService) {
        this.matchService = matchService.orElse(null);
    }

    @Override
    public Match convert(@Nonnull String source) {
        return matchService.findByCode(source)
            .orElseThrow(() -> new IllegalArgumentException("match with code %s does not exist".formatted(source)));
    }
}
