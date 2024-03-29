package us.lsi.dp1.newcorporder.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import us.lsi.dp1.newcorporder.auth.jwt.AuthEntryPointJwt;
import us.lsi.dp1.newcorporder.auth.jwt.AuthTokenFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    public static final String ADMIN = "ADMIN";
    private final AuthEntryPointJwt unauthorizedHandler;

    public SecurityConfiguration(AuthEntryPointJwt unauthorizedHandler) {
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.cors(withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(unauthorizedHandler))

            .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                .requestMatchers("/resources/**", "/webjars/**", "/static/**", "/swagger-resources/**").permitAll()
                .requestMatchers("/", "/api/v1/auth/**", "/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll()

                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/users")).authenticated()
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/users/{username}")).authenticated()
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/users/{username}/picture")).authenticated()
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/users/{username}/friends")).authenticated()
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/users/{username}")).authenticated()
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/api/v1/users/{username}/**")).authenticated()
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/api/v1/users/{username}")).hasAuthority(ADMIN)
                .requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/users/friendships/**")).authenticated()

                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/players/{username}/stats")).authenticated()
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/players/{username}/lastMatches")).authenticated()
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/players/ranking")).authenticated()

                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/matches")).hasAuthority(ADMIN)
                .requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/matches/**")).authenticated()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/notifications/**")).authenticated()

                .requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/metrics")).authenticated()
                .requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/notifications/**")).authenticated()

                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/achievements/**")).authenticated()
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/v1/achievements/**")).hasAuthority(ADMIN)
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/api/v1/achievements/**")).hasAuthority(ADMIN)
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/api/v1/achievements/**")).hasAuthority(ADMIN)
                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()

                .anyRequest().denyAll())
            .addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
