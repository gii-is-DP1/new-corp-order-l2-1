package us.lsi.dp1.newcorporder.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.apache.commons.lang3.ArrayUtils;
import us.lsi.dp1.newcorporder.authority.Authority;
import us.lsi.dp1.newcorporder.friends.FriendshipRequest;
import us.lsi.dp1.newcorporder.misc.Notification;
import us.lsi.dp1.newcorporder.model.BaseEntity;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
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

    private String picture;

    private LocalDate firstSeen;

    private LocalDate lastSeen;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sender")
    private Set<FriendshipRequest> sentFriendshipRequests;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receiver")
    private Set<FriendshipRequest> receivedFriendshipRequests;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Notification> notifications;

    public Boolean hasAuthority(String auth) {
        return authority.getName().equals(auth);
    }

    public Boolean hasAnyAuthority(String... authorities) {
        return ArrayUtils.contains(authorities, this.authority.getName());
    }
}
