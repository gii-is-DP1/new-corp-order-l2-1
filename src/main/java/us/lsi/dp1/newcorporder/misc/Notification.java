package us.lsi.dp1.newcorporder.misc;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import us.lsi.dp1.newcorporder.model.BaseEntity;
import us.lsi.dp1.newcorporder.user.User;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "notifications")
public class Notification extends BaseEntity {
    @NotNull
    Instant sentAt;

    @NotNull
    @Size(max=50)
    String title;

    @NotNull
    String message;

    String imageUrl;

    @NotNull
    NotificacionState state;

    @ManyToOne
    User user;
}
