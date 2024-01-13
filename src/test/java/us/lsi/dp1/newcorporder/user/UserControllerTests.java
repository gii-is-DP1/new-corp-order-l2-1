package us.lsi.dp1.newcorporder.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
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
import us.lsi.dp1.newcorporder.exception.AccessDeniedException;
import us.lsi.dp1.newcorporder.exception.ResourceNotFoundException;
import us.lsi.dp1.newcorporder.friendship.FriendshipService;
import us.lsi.dp1.newcorporder.user.payload.response.UserView;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
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
    private static final String TEST_USER_NAME = "JohnDoe";
    private static final int TEST_AUTH_ID = 1;
    private static final String BASE_URL = "/api/v1/users";

    @SuppressWarnings("unused")
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @MockBean
    private FriendshipService friendshipService;

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

        user = User.builder()
            .id(1)
            .username("user")
            .password("password")
            .authority(auth)
            .friendships(Set.of())
            .build();

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

    @Test
    @WithMockUser("JohnDoe")
    void shouldFindAll() throws Exception {
        UserView sara = UserView.builder()
            .username("Sara")
            .build();

        UserView juan = UserView.builder()
            .username("Juan")
            .build();

        when(this.userService.getAllUsers(any(), any())).thenReturn(List.of(UserView.reduced(user), sara, juan));

        mockMvc.perform(get(BASE_URL)).andExpect(status().isOk()).andExpect(jsonPath("$.size()").value(3))
            .andExpect(jsonPath("$[0].username").value("user"))
            .andExpect(jsonPath("$[1].username").value("Sara"))
            .andExpect(jsonPath("$[2].username").value("Juan"));
    }

    @Test
    @WithMockUser("JohnDoe")
    void shouldReturnUser() throws Exception {
        when(this.userService.findUser(TEST_USER_NAME)).thenReturn(user);
        mockMvc.perform(get(BASE_URL + "/{id}", TEST_USER_NAME)).andExpect(status().isOk())
            .andExpect(jsonPath("$.username").value(user.getUsername()))
            .andExpect(jsonPath("$.authority").value(user.getAuthority().getName()));
    }

    @Test
    @WithMockUser("JohnDoe")
    void shouldReturnNotFoundUser() throws Exception {
        when(this.userService.findUser(TEST_USER_NAME)).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(get(BASE_URL + "/{id}", TEST_USER_NAME)).andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser("JohnDoe")
    void shouldDeleteOtherUser() throws Exception {
        logged.setId(2);

        when(this.userService.findUser(TEST_USER_NAME)).thenReturn(user);
        doNothing().when(this.userService).deleteUser(TEST_USER_NAME);

        mockMvc.perform(delete(BASE_URL + "/{id}", TEST_USER_NAME).with(csrf())).andExpect(status().isOk())
            .andExpect(jsonPath("$.message").value("User deleted!"));
    }

    @Test
    @WithMockUser("JohnDoe")
    void shouldNotDeleteLoggedUser() throws Exception {
        logged.setId(TEST_USER_ID);

        when(this.userService.findUser(TEST_USER_NAME)).thenReturn(user);
        doNothing().when(this.userService).deleteUser(TEST_USER_NAME);

        mockMvc.perform(delete(BASE_URL + "/{id}", TEST_USER_NAME).with(csrf())).andExpect(status().isForbidden())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof AccessDeniedException));
    }

}
