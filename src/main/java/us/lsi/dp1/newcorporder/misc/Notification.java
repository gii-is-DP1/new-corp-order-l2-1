package us.lsi.dp1.newcorporder.misc;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @ManyToOne(optional = false)
    @JsonIgnore
    private User user;

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

}
