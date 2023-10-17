package us.lsi.dp1.newcorporder.user;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import us.lsi.dp1.newcorporder.authority.Authority;
import us.lsi.dp1.newcorporder.authority.AuthorityService;
import us.lsi.dp1.newcorporder.exceptions.ResourceNotFoundException;
import org.springframework.transaction.annotation.Transactional;

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
		assertEquals(4, auths.size());
	}

	@Test
	void shouldFindAuthoritiesByAuthority() {
		Authority auth = this.authService.findByName("VET");
		assertEquals("VET", auth.getName());
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
