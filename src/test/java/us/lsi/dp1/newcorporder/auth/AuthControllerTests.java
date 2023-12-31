package us.lsi.dp1.newcorporder.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.MockMvc;
import us.lsi.dp1.newcorporder.auth.jwt.JwtUtils;
import us.lsi.dp1.newcorporder.auth.payload.request.LoginRequest;
import us.lsi.dp1.newcorporder.auth.payload.request.SignupRequest;
import us.lsi.dp1.newcorporder.user.User;
import us.lsi.dp1.newcorporder.user.UserService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AuthController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = {
    SecurityAutoConfiguration.class})
class AuthControllerTests {

    private static final String BASE_URL = "/api/v1/auth";

    @SuppressWarnings("unused")
    @Autowired
    private AuthController authController;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private LoginRequest loginRequest;
    private SignupRequest signupRequest;
    private ApplicationUserDetails userDetails;
    private String token;

    @BeforeEach
    void setup() {
        loginRequest = new LoginRequest();
        loginRequest.setUsername("owner");
        loginRequest.setPassword("password");

        signupRequest = new SignupRequest();
        signupRequest.setUsername("username");
        signupRequest.setPassword("password");
        signupRequest.setAuthority("OWNER");
        signupRequest.setEmail("email@gmail.com");

        User user = User.builder()
            .id(1)
            .username(loginRequest.getUsername())
            .password(loginRequest.getPassword())
            .build();

        userDetails = new ApplicationUserDetails(user, List.of(new SimpleGrantedAuthority("OWNER")));
        token = "JWT TOKEN";
    }

    @Test
    void shouldAuthenticateUser() throws Exception {
        Authentication auth = Mockito.mock(Authentication.class);

        when(this.jwtUtils.generateJwtToken(any(Authentication.class))).thenReturn(token);
        when(this.authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(auth);
        Mockito.doReturn(userDetails).when(auth).getPrincipal();

        mockMvc.perform(post(BASE_URL + "/login").with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest))).andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value(loginRequest.getUsername()))
            .andExpect(jsonPath("$.id").value(userDetails.getUser().getId())).andExpect(jsonPath("$.token").value(token));
    }

    @Test
    void shouldValidateToken() throws Exception {
        when(this.jwtUtils.validateJwtToken(token)).thenReturn(true);

        mockMvc.perform(get(BASE_URL + "/validate").with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .param("token", token)).andExpect(status().isOk())
            .andExpect(jsonPath("$").value(true));
    }

    @Test
    void shouldNotValidateToken() throws Exception {
        when(this.jwtUtils.validateJwtToken(token)).thenReturn(false);

        mockMvc.perform(get(BASE_URL + "/validate").with(csrf()).contentType(MediaType.APPLICATION_JSON)
                .param("token", token)).andExpect(status().isOk())
            .andExpect(jsonPath("$").value(false));
    }

    @Test
    void shouldRegisterUser() throws Exception {
        when(this.userService.existsUser(signupRequest.getUsername())).thenReturn(false);
        doNothing().when(this.authService).registerUser(signupRequest);

        mockMvc.perform(post(BASE_URL + "/signup")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signupRequest)))
            .andExpect(status().isCreated());
    }
}
