package us.lsi.dp1.newcorporder.auth.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String authority;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

}
