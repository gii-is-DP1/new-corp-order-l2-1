package us.lsi.dp1.newcorporder.friends;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import us.lsi.dp1.newcorporder.model.BaseEntity;
import us.lsi.dp1.newcorporder.user.User;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "frienship_request")
public class FriendshipRequest extends BaseEntity {
    @NotNull
    @Column(name = "sent_at")
    private Instant sentAt;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

}
