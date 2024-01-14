package us.lsi.dp1.newcorporder.match.configuration;

import jakarta.annotation.Nonnull;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;
import us.lsi.dp1.newcorporder.bind.FromPathVariable;
import us.lsi.dp1.newcorporder.match.Match;
import us.lsi.dp1.newcorporder.match.MatchService;

import java.util.Map;
import java.util.Optional;

@Component
public class MatchArgumentResolver implements HandlerMethodArgumentResolver {

    private final MatchService matchService;

    public MatchArgumentResolver(Optional<MatchService> matchService) {
        this.matchService = matchService.orElse(null);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(FromPathVariable.class)
               && Match.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(@Nonnull MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  @Nonnull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        String code = getPathVariable(webRequest, parameter.getParameterName());

        return matchService.findByCode(code)
            .orElseThrow(() -> new IllegalArgumentException("match with code %s already ended or does not exist".formatted(code)));
    }

    @SuppressWarnings("unchecked")
    private String getPathVariable(NativeWebRequest webRequest, String name) {
        Map<String, String> pathVariables = (Map<String, String>) webRequest
            .getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);

        return pathVariables != null ? pathVariables.get(name) : null;
    }
}
