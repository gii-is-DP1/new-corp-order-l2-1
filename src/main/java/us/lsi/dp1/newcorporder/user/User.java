package us.lsi.dp1.newcorporder.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.ArrayUtils;
import us.lsi.dp1.newcorporder.authority.Authority;
import us.lsi.dp1.newcorporder.friendship.Friendship;
import us.lsi.dp1.newcorporder.friendship.FriendshipRequest;
import us.lsi.dp1.newcorporder.model.BaseEntity;
import us.lsi.dp1.newcorporder.notification.Notification;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class User extends BaseEntity {

    @Size(max = 32)
    @Column(unique = true)
    @NotEmpty private String username;

    @Size(max = 32)
    @Column(unique = true)
    @NotEmpty private String email;

    @NotEmpty private String password;

    @ManyToOne(optional = false)
    @JoinColumn(name = "authority")
    @NotNull private Authority authority;

    private Integer picture;

    private Instant firstSeen;

    private Instant lastSeen;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Friendship> friendships;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sender")
    private Set<FriendshipRequest> sentFriendshipRequests;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receiver")
    private Set<FriendshipRequest> receivedFriendshipRequests;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Notification> notifications;

    public Boolean hasAuthority(String auth) {
        return authority.getName().equals(auth);
    }

    public Boolean hasAnyAuthority(String... authorities) {
        return ArrayUtils.contains(authorities, this.authority.getName());
    }
}
