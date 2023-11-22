package us.lsi.dp1.newcorporder.player;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import us.lsi.dp1.newcorporder.model.NamedEntity;

import java.time.LocalDate;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@SuperBuilder
@Table(name = "players")
@Entity
public class Player extends NamedEntity {

    @Size(max=32)
    private String username;

    @Size(max=32)
    private String email;

    private String picture;

    private String password;

    private LocalDate firstSeen;

    private LocalDate lastSeen;

    public Player() {
    }
}
