package us.lsi.dp1.newcorporder.friends;

import jakarta.persistence.*;
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
public class Friendship extends NamedEntity {

    @Column(name = "since")
    private Instant since;

    @ManyToMany(mappedBy = "friendship")

    private Set<User> users;
}
