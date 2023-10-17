package us.lsi.dp1.newcorporder.match.conglomerate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConglomerateShare {

    private final Conglomerate conglomerate;
    private ShareState state = ShareState.UNUSED;

    public ConglomerateShare(Conglomerate conglomerate) {
        this.conglomerate = conglomerate;
    }
}
