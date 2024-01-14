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
import us.lsi.dp1.newcorporder.match.consultant.ConsultantType;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.model.BaseEntity;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "consultant_stats")
@EqualsAndHashCode(callSuper = true, of = {})
public class ConsultantStats extends BaseEntity {

    public static List<ConsultantStats> create(MatchPlayer player) {
        return Arrays.stream(ConsultantType.values())
            .map(type -> ConsultantStats.create(player, type))
            .toList();
    }

    public static ConsultantStats create(MatchPlayer player, ConsultantType type) {
        return ConsultantStats.builder()
            .type(type)
            .timesUsed(player.getConsultantUses().count(type))
            .build();
    }

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private PlayerMatchStats playerMatchStats;

    @Enumerated(EnumType.STRING)
    @NotNull private ConsultantType type;

    @NotNull private Integer timesUsed;
}
