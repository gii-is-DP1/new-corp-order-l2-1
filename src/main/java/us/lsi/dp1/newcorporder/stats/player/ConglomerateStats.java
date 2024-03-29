package us.lsi.dp1.newcorporder.stats.player;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.model.BaseEntity;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "conglomerate_stats")
@EqualsAndHashCode(callSuper = true, of = {})
public class ConglomerateStats extends BaseEntity {

    public static List<ConglomerateStats> create(MatchPlayer player, PlayerMatchStats playerMatchStats) {
        return Arrays.stream(Conglomerate.values())
            .map(conglomerate -> ConglomerateStats.create(player, playerMatchStats, conglomerate))
            .toList();
    }

    public static ConglomerateStats create(MatchPlayer player, PlayerMatchStats playerMatchStats, Conglomerate conglomerate) {
        return ConglomerateStats.builder()
            .playerMatchStats(playerMatchStats)
            .conglomerate(conglomerate)
            .sharesUsed(player.getShareUses().count(conglomerate))
            .agentsUsed(player.getAgentUses().count(conglomerate))
            .build();
    }

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PlayerMatchStats playerMatchStats;

    @Enumerated(EnumType.STRING)
    @NotNull private Conglomerate conglomerate;

    @NotNull private Integer sharesUsed;

    @NotNull private Integer agentsUsed;
}
