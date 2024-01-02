package us.lsi.dp1.newcorporder.notification;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import us.lsi.dp1.newcorporder.model.BaseEntity;
import us.lsi.dp1.newcorporder.user.User;

import java.time.Instant;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Table(name = "notifications")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Notification extends BaseEntity {

    @NotNull
    @ManyToOne(optional = false)
    @JsonIgnore
    private User user;

    @NotNull
    @Column(name = "sent_at")
    private Instant sentAt;

    @NotNull
    @Column(name = "state")
    @Builder.Default
    private NotificacionState state = NotificacionState.NOT_SEEN;

}
