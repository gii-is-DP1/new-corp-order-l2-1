package us.lsi.dp1.newcorporder.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.ArrayUtils;
import us.lsi.dp1.newcorporder.authority.Authority;
import us.lsi.dp1.newcorporder.friends.Friendship;
import us.lsi.dp1.newcorporder.friends.FriendshipRequest;
import us.lsi.dp1.newcorporder.misc.Notification;
import us.lsi.dp1.newcorporder.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Size(max=32) @Column(unique = true) @NotNull
    private String username;

    @Size(max=32) @Column(unique = true) @NotNull
    private String email;

    private String picture;

    @NotNull
    private String password;

    private LocalDate firstSeen;

    private LocalDate lastSeen;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "authority")
    Authority authority;

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
