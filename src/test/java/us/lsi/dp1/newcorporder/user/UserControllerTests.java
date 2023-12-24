package us.lsi.dp1.newcorporder.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import us.lsi.dp1.newcorporder.authority.Authority;
import us.lsi.dp1.newcorporder.authority.AuthorityService;
import us.lsi.dp1.newcorporder.configuration.SecurityConfiguration;
import us.lsi.dp1.newcorporder.exceptions.AccessDeniedException;
import us.lsi.dp1.newcorporder.exceptions.ResourceNotFoundException;

import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for the {@link UserController}
 */
@WebMvcTest(controllers = UserController.class, excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfigurer.class), excludeAutoConfiguration = SecurityConfiguration.class)
class UserControllerTests {

    private static final int TEST_USER_ID = 1;
    private static final int TEST_AUTH_ID = 1;
    private static final String BASE_URL = "/api/v1/users";

    @SuppressWarnings("unused")
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @MockBean
    private AuthorityService authService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    private Authority auth;
    private User user, logged;

    @BeforeEach
    void setup() {
        auth = new Authority();
        auth.setId(TEST_AUTH_ID);
        auth.setName("VET");

        user = new User();
        user.setId(1);
        user.setUsername("user");
        user.setPassword("password");
        user.setAuthority(auth);

        when(this.userService.findCurrentUser()).thenReturn(getUserFromDetails(
            (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
    }

    private User getUserFromDetails(UserDetails details) {
        logged = new User();
        logged.setUsername(details.getUsername());
        logged.setPassword(details.getPassword());
        Authority aux = new Authority();
        for (GrantedAuthority auth : details.getAuthorities()) {
            aux.setName(auth.getAuthority());
        }
        logged.setAuthority(aux);
        return logged;
    }

    @Disabled("until pagination is added")
    @Test
    @WithMockUser("admin")
    void shouldFindAll() throws Exception {
        User sara = new User();
        sara.setId(2);
        sara.setUsername("Sara");

        User juan = new User();
        juan.setId(3);
        juan.setUsername("Juan");

        when(this.userService.findAll()).thenReturn(List.of(user, sara, juan));

        mockMvc.perform(get(BASE_URL)).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(3))
            .andExpect(jsonPath("$[?(@.id == 1)].username").value("user"))
            .andExpect(jsonPath("$[?(@.id == 2)].username").value("Sara"))
            .andExpect(jsonPath("$[?(@.id == 3)].username").value("Juan"));
    }

    @Disabled("until pagination is added")
    @Test
    @WithMockUser("admin")
    void shouldFindAllWithAuthority() throws Exception {
        Authority aux = new Authority();
        aux.setId(2);
        aux.setName("AUX");

        User sara = new User();
        sara.setId(2);
        sara.setUsername("Sara");
        sara.setAuthority(aux);

        User juan = new User();
        juan.setId(3);
        juan.setUsername("Juan");
        juan.setAuthority(auth);

        when(this.userService.findAllByAuthority(auth.getName())).thenReturn(List.of(user, juan));

        mockMvc.perform(get(BASE_URL).param("auth", "VET")).andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2)).andExpect(jsonPath("$[?(@.id == 1)].username").value("user"))
            .andExpect(jsonPath("$[?(@.id == 3)].username").value("Juan"));
    }

    @Disabled("until pagination is added")
    @Test
    @WithMockUser("admin")
    void shouldFindAllAuths() throws Exception {
        Authority aux = new Authority();
        aux.setId(2);
        aux.setName("AUX");

        when(this.authService.findAll()).thenReturn(List.of(auth, aux));

        mockMvc.perform(get(BASE_URL + "/authorities")).andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value(2))
            .andExpect(jsonPath("$[?(@.id == 1)].name").value("VET"))
            .andExpect(jsonPath("$[?(@.id == 2)].name").value("AUX"));
    }

    @Test
    @WithMockUser("admin")
    void shouldReturnUser() throws Exception {
        when(this.userService.findUser(TEST_USER_ID)).thenReturn(user);
        mockMvc.perform(get(BASE_URL + "/{id}", TEST_USER_ID)).andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(TEST_USER_ID))
            .andExpect(jsonPath("$.username").value(user.getUsername()))
            .andExpect(jsonPath("$.authority.name").value(user.getAuthority().getName()));
    }

    @Test
    @WithMockUser("admin")
    void shouldReturnNotFoundUser() throws Exception {
        when(this.userService.findUser(TEST_USER_ID)).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get(BASE_URL + "/{id}", TEST_USER_ID)).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser("admin")
    void shouldDeleteOtherUser() throws Exception {
        logged.setId(2);

        when(this.userService.findUser(TEST_USER_ID)).thenReturn(user);
        doNothing().when(this.userService).deleteUser(TEST_USER_ID);

        mockMvc.perform(delete(BASE_URL + "/{id}", TEST_USER_ID).with(csrf())).andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("User deleted!"));
    }

    @Test
    @WithMockUser("admin")
    void shouldNotDeleteLoggedUser() throws Exception {
        logged.setId(TEST_USER_ID);

        when(this.userService.findUser(TEST_USER_ID)).thenReturn(user);
        doNothing().when(this.userService).deleteUser(TEST_USER_ID);

        mockMvc.perform(delete(BASE_URL + "/{id}", TEST_USER_ID).with(csrf())).andExpect(status().isForbidden())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof AccessDeniedException));
    }

}
