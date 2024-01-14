package us.lsi.dp1.newcorporder.friendship;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import us.lsi.dp1.newcorporder.model.BaseEntity;
import us.lsi.dp1.newcorporder.user.User;

import java.time.Instant;

@Entity
@Data
@Table(name = "friendship_requests")
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, of = {})
public class FriendshipRequest extends BaseEntity {

    @NotNull
    @Column(name = "sent_at")
    private Instant sentAt;

    @ManyToOne
    private User sender;

    @ManyToOne
    private User receiver;

}
