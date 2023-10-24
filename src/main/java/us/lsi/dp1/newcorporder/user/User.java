package us.lsi.dp1.newcorporder.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import org.apache.commons.lang3.ArrayUtils;
import us.lsi.dp1.newcorporder.authority.Authority;
import us.lsi.dp1.newcorporder.friends.Friendship;
import us.lsi.dp1.newcorporder.friends.FriendshipRequest;
import us.lsi.dp1.newcorporder.misc.Notification;
import us.lsi.dp1.newcorporder.model.BaseEntity;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(unique = true)
    private String username;

    private String password;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "authority")
    private Authority authority;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "users")
    private Set<Friendship> friendships;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "users")
    private Set<FriendshipRequest> sentFriendshipRequests;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "users")
    private Set<FriendshipRequest> receivedFriendshipRequests;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "users")
    private Set<Notification> notifications;

    public Boolean hasAuthority(String auth) {
        return authority.getName().equals(auth);
    }

    public Boolean hasAnyAuthority(String... authorities) {
        return ArrayUtils.contains(authorities, this.authority.getName());
    }
}
