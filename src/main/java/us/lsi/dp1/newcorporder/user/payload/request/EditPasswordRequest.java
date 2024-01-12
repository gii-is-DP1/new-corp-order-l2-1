package us.lsi.dp1.newcorporder.user.payload.request;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EditPasswordRequest {

    private String newPassword;

    private String oldPassword;

}
