package us.lsi.dp1.newcorporder.player;
import jakarta.persistence.*;
import us.lsi.dp1.newcorporder.user.User;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.lang3.ArrayUtils;
import us.lsi.dp1.newcorporder.authority.Authority;
import us.lsi.dp1.newcorporder.model.BaseEntity;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "players")
public class Player extends BaseEntity {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user", referencedColumnName = "username")
    private User user;



    public String toString(){
        return user.getUsername();
    }

    public static Player createDefault() {

        Player player = new Player();


        player.setId(0);

        player.setUser(null);

        return player;
    }




}
