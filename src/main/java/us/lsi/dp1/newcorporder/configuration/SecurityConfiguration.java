package us.lsi.dp1.newcorporder.configuration;

import org.springframework.beans.factory.annotation.Autowired;
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
import us.lsi.dp1.newcorporder.auth.ApplicationUserDetailsService;
import us.lsi.dp1.newcorporder.auth.jwt.AuthEntryPointJwt;
import us.lsi.dp1.newcorporder.auth.jwt.AuthTokenFilter;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
    ApplicationUserDetailsService userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Autowired
	DataSource dataSource;

	private static final String ADMIN = "ADMIN";


	@Bean
	protected SecurityFilterChain configure(HttpSecurity http) throws Exception {

		http
			.cors(withDefaults())
			.csrf(AbstractHttpConfigurer::disable)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.headers((headers) -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
			.exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(unauthorizedHandler))

			.authorizeHttpRequests(authorizeRequests ->	authorizeRequests
			.requestMatchers("/resources/**", "/webjars/**", "/static/**", "/swagger-resources/**").permitAll()
			.requestMatchers( "/", "/oups","/api/v1/auth/**","/v3/api-docs/**","/swagger-ui.html","/swagger-ui/**").permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/users/{username}")).permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/api/v1/users/{username}")).permitAll()
                .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/api/v1/users/{username}")).hasAuthority(ADMIN)
            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/v1/achievements/**")).hasAuthority(ADMIN)
            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.PUT, "/api/v1/achievements/**")).hasAuthority(ADMIN)
            .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE, "/api/v1/achievements/**")).hasAuthority(ADMIN)
			.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
			.anyRequest().authenticated())

			.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
		return config.getAuthenticationManager();
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}



}
