package us.lsi.dp1.newcorporder.friends;

import com.fasterxml.jackson.databind.ser.Serializers;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import us.lsi.dp1.newcorporder.model.BaseEntity;
import us.lsi.dp1.newcorporder.model.NamedEntity;
import us.lsi.dp1.newcorporder.user.User;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "friendship")
public class Friendship extends BaseEntity {
    @NotNull
    @Column(name = "since")
    Instant since;

    @ManyToOne
    User friendshipUser;

    @ManyToOne
    User friend;
}
