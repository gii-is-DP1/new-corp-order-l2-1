package us.lsi.dp1.newcorporder.friends;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import us.lsi.dp1.newcorporder.model.BaseEntity;
import us.lsi.dp1.newcorporder.user.User;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "friendship")
public class Friendship extends BaseEntity {
    @NotNull
    @Column(name = "since")
    private Instant since;

    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToOne
    private User friend;
}
