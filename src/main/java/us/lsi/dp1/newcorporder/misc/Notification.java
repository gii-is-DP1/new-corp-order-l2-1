package us.lsi.dp1.newcorporder.misc;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import us.lsi.dp1.newcorporder.model.BaseEntity;
import us.lsi.dp1.newcorporder.user.User;

import java.time.Instant;

@Entity
@Getter
@Setter
@Table(name = "notification")
public class Notification extends BaseEntity {

    @NotNull
    @Column(name = "sent_at")
    private Instant sentAt;

    @NotNull
    @Column(name = "title")
    @Size(max = 50)
    private String title;

    @NotNull
    @Column(name = "message")
    private String message;

    @Column(name = "image_url")
    private String imageUrl;

    @NotNull
    @Column(name = "state")
    private NotificacionState state;

    @ManyToOne
    private User user;
}
