package us.lsi.dp1.newcorporder.player;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;
@XmlRootElement
public class Players {

    private List<Player> players;

    @XmlElement
    public List<Player> getPlayerList() {
        if (players == null) {
            players = new ArrayList<>();
        }
        return players;
    }


}
