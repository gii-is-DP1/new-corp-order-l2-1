package us.lsi.dp1.newcorporder.notification;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import us.lsi.dp1.newcorporder.user.User;
import us.lsi.dp1.newcorporder.user.payload.response.UserView;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "invite_notifications")
public class InviteNotification extends Notification {

    @NotNull
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User sender;

    @NotNull
    private String matchCode;

    public UserView getSender() {
        return UserView.reduced(sender);
    }
}
