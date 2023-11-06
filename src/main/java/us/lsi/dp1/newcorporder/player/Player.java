package us.lsi.dp1.newcorporder.player;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import us.lsi.dp1.newcorporder.model.BaseEntity;
import us.lsi.dp1.newcorporder.user.User;

@Getter
@Setter
@Entity
@Table(name = "players")

public class Player extends BaseEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user", referencedColumnName = "username") private User user;

    public String toString() {return user.getUsername();}


}
