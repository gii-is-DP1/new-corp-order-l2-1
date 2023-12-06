package us.lsi.dp1.newcorporder.configuration;

import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import us.lsi.dp1.newcorporder.player.PlayerArgumentResolver;

import java.util.List;

@Component
public class WebConfiguration implements WebMvcConfigurer {

    private final GenericIdToEntityConverter idToEntityConverter;
    private final PlayerArgumentResolver playerArgumentResolver;

    public WebConfiguration(GenericIdToEntityConverter idToEntityConverter, PlayerArgumentResolver playerArgumentResolver) {
        this.idToEntityConverter = idToEntityConverter;
        this.playerArgumentResolver = playerArgumentResolver;
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(playerArgumentResolver);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(idToEntityConverter);
    }
}
