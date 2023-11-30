package us.lsi.dp1.newcorporder.player;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import us.lsi.dp1.newcorporder.model.NamedEntity;
import us.lsi.dp1.newcorporder.user.User;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@SuperBuilder
@Table(name = "players")
@Entity
public class Player extends NamedEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "users", referencedColumnName = "id")
    private User user;

    public Integer getId(){
        return user.getId();
    }

    public Player() {

    }
}
