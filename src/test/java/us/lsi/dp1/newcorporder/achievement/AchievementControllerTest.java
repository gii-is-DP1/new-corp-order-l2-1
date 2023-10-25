package us.lsi.dp1.newcorporder.achievement;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import us.lsi.dp1.newcorporder.auth.AuthController;
import us.lsi.dp1.newcorporder.auth.payload.request.LoginRequest;
import us.lsi.dp1.newcorporder.authority.Authority;
import us.lsi.dp1.newcorporder.configuration.jwt.JwtUtils;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AchievementControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void getWithoutAuthTokenIsUnauthorized() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/achievements/")
                //.with(user("owner1").password("0wn3r"))
                //.header("authorization", "Bearer "+ token)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
    }
}
