package us.lsi.dp1.newcorporder.friends;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import us.lsi.dp1.newcorporder.model.BaseEntity;
import us.lsi.dp1.newcorporder.user.User;

import java.time.Instant;

@Entity
@Getter
@Setter
public class FriendshipRequest extends BaseEntity {
    @NotNull
    Instant sentAt;

    @NotNull
    @ManyToOne
    User sender;

    @NotNull
    @ManyToOne
    User receiver;

}
