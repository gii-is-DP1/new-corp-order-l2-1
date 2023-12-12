package us.lsi.dp1.newcorporder.configuration;

import org.springframework.core.annotation.Order;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import us.lsi.dp1.newcorporder.match.configuration.MatchArgumentResolver;
import us.lsi.dp1.newcorporder.player.PlayerArgumentResolver;

import java.util.List;

@Component
@Order(0)
public class WebConfiguration implements WebMvcConfigurer {

    private final GenericIdToEntityConverter idToEntityConverter;
    private final MatchArgumentResolver matchArgumentResolver;
    private final PlayerArgumentResolver playerArgumentResolver;

    public WebConfiguration(GenericIdToEntityConverter idToEntityConverter, MatchArgumentResolver matchArgumentResolver, PlayerArgumentResolver playerArgumentResolver) {
        this.idToEntityConverter = idToEntityConverter;
        this.matchArgumentResolver = matchArgumentResolver;
        this.playerArgumentResolver = playerArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(matchArgumentResolver);
        resolvers.add(playerArgumentResolver);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(idToEntityConverter);
    }
}
