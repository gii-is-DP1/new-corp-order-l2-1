package us.lsi.dp1.newcorporder.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import us.lsi.dp1.newcorporder.authority.Authority;
import us.lsi.dp1.newcorporder.authority.AuthorityService;
import us.lsi.dp1.newcorporder.exceptions.ResourceNotFoundException;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

//@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
@SpringBootTest
@AutoConfigureTestDatabase
class AuthorityServiceTests {

//	@Autowired
//	private UserService userService;

	@Autowired
	private AuthorityService authService;

	@Test
	void shouldFindAllAuthorities() {
		List<Authority> auths = (List<Authority>) this.authService.findAll();
        assertEquals(2, auths.size());
	}

	@Test
	void shouldFindAuthoritiesByAuthority() {
        Authority auth = this.authService.findByName("ADMIN");
        assertEquals("ADMIN", auth.getName());
	}

	@Test
	void shouldNotFindAuthoritiesByIncorrectAuthority() {
		assertThrows(ResourceNotFoundException.class, () -> this.authService.findByName("authnotexists"));
	}

	@Test
	@Transactional
	void shouldInsertAuthorities() {
		int count = ((Collection<Authority>) this.authService.findAll()).size();

		Authority auth = new Authority();
		auth.setName("CLIENT");

		this.authService.save(auth);
		assertNotEquals(0, auth.getId().longValue());
		assertNotNull(auth.getId());

		int finalCount = ((Collection<Authority>) this.authService.findAll()).size();
		assertEquals(count + 1, finalCount);
	}

//	@Test
//	@Transactional
//	void shouldAddAuthoritiesToUser() {
//		User user = userService.findUser("owner1");
//		assertEquals("OWNER" ,user.getAuthority().getAuthority());
//
//		this.authService.saveAuthorities("owner1", "TEST");
//		assertEquals("TEST" ,user.getAuthority().getAuthority());
//
//	}

}
