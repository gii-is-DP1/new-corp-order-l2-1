package us.lsi.dp1.newcorporder.player;

import jakarta.annotation.Nonnull;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import us.lsi.dp1.newcorporder.auth.Authenticated;

import java.util.Optional;

@Component
public class PlayerArgumentResolver implements HandlerMethodArgumentResolver {

    private final PlayerService playerService;

    public PlayerArgumentResolver(Optional<PlayerService> playerService) {
        this.playerService = playerService.orElse(null);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Authenticated.class)
               && Player.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(@Nonnull MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  @Nonnull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        return playerService.getAuthenticatedPlayer();
    }
}
