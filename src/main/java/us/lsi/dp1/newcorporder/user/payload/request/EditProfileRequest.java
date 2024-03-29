package us.lsi.dp1.newcorporder.user.payload.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EditProfileRequest {

    @Size(max = 32)
    private String username = "";

    @Size(max = 32)
    private String email = "";

    private String password = "";

    private String picture = "";
}
