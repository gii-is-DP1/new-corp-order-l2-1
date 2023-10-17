package us.lsi.dp1.newcorporder.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import us.lsi.dp1.newcorporder.auth.payload.request.SignupRequest;
import us.lsi.dp1.newcorporder.authority.AuthorityService;
import us.lsi.dp1.newcorporder.user.User;
import us.lsi.dp1.newcorporder.user.UserService;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AuthServiceTests {

	@Autowired
	protected AuthService authService;
	@Autowired
	protected UserService userService;
	@Autowired
	protected AuthorityService authorityService;

	@Test
	@Transactional
	public void shouldCreateAdminUser() {
		SignupRequest request = createRequest("ADMIN", "admin2");
		int userFirstCount = ((Collection<User>) this.userService.findAll()).size();
		this.authService.createUser(request);
		int userLastCount = ((Collection<User>) this.userService.findAll()).size();
		assertEquals(userFirstCount + 1, userLastCount);
	}

	private SignupRequest createRequest(String auth, String username) {
		SignupRequest request = new SignupRequest();
		request.setAuthority(auth);
		request.setFirstName("prueba");
		request.setLastName("prueba");
		request.setPassword("prueba");
		request.setUsername(username);

		if(auth == "OWNER") {
			User clinicOwnerUser = new User();
			clinicOwnerUser.setUsername("clinicOwnerTest");
			clinicOwnerUser.setPassword("clinicOwnerTest");
			clinicOwnerUser.setAuthority(authorityService.findByName("CLINIC_OWNER"));
			userService.saveUser(clinicOwnerUser);
		}

		return request;
	}

}
