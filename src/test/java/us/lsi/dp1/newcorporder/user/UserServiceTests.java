package us.lsi.dp1.newcorporder.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.transaction.annotation.Transactional;
import us.lsi.dp1.newcorporder.authority.AuthorityService;
import us.lsi.dp1.newcorporder.exception.ResourceNotFoundException;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
class UserServiceTests {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthorityService authService;

	@Test
    @WithMockUser(username = "JohnDoe")
	void shouldFindCurrentUser() {
		User user = this.userService.findCurrentUser();
        assertEquals("JohnDoe", user.getUsername());
	}

	@Test
    @WithMockUser(username = "ghost")
	void shouldNotFindCorrectCurrentUser() {
		assertThrows(ResourceNotFoundException.class, () -> this.userService.findCurrentUser());
	}

	@Test
	void shouldNotFindAuthenticated() {
		assertThrows(ResourceNotFoundException.class, () -> this.userService.findCurrentUser());
	}

	@Test
	void shouldFindAllUsers() {
		List<User> users = (List<User>) this.userService.findAll();
        assertEquals(6, users.size());
	}

	@Test
	void shouldFindUsersByUsername() {
        User user = this.userService.findUser("JohnDoe");
        assertEquals("JohnDoe", user.getUsername());
	}

	@Test
	void shouldFindUsersByAuthority() {
        List<User> users = (List<User>) this.userService.findAllByAuthority("USER");
        assertEquals(5, users.size());

		List<User> admins = (List<User>) this.userService.findAllByAuthority("ADMIN");
		assertEquals(1, admins.size());

	}

	@Test
	void shouldNotFindUserByIncorrectUsername() {
		assertThrows(ResourceNotFoundException.class, () -> this.userService.findUser("usernotexists"));
	}

	@Test
	void shouldFindSingleUser() {
        User user = this.userService.findUser(3);
        assertEquals("SamSmith", user.getUsername());
	}

	@Test
	void shouldNotFindSingleUserWithBadID() {
		assertThrows(ResourceNotFoundException.class, () -> this.userService.findUser(100));
	}

	@Test
	void shouldExistUser() {
        assertEquals(true, this.userService.existsUser("SamSmith"));
	}

	@Test
	void shouldNotExistUser() {
        assertEquals(false, this.userService.existsUser("japarejo"));
	}

	@Test
	@Transactional
	void shouldInsertUser() {
		int count = ((Collection<User>) this.userService.findAll()).size();

		User user = new User();
		user.setUsername("Sam");
		user.setPassword("password");
        user.setEmail("sam@gmail.com");
		user.setAuthority(authService.findByName("ADMIN"));

		this.userService.saveUser(user);
		assertNotEquals(0, user.getId().longValue());
		assertNotNull(user.getId());

		int finalCount = ((Collection<User>) this.userService.findAll()).size();
		assertEquals(count + 1, finalCount);
	}
}
