package us.lsi.dp1.newcorporder.stats;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.player.MatchPlayer;
import us.lsi.dp1.newcorporder.model.BaseEntity;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "ability_stats")
@EqualsAndHashCode(callSuper = true, of = {})
public class AbilityStats extends BaseEntity {

    public static List<AbilityStats> create(MatchPlayer player) {
        return Arrays.stream(CompanyType.values())
            .map(type -> AbilityStats.create(player, type))
            .toList();
    }

    public static AbilityStats create(MatchPlayer player, CompanyType type) {
        return AbilityStats.builder()
            .type(type)
            .timesUsed(player.getAbilityUses().count(type))
            .build();
    }

    @ManyToOne
    private PlayerMatchStats playerMatchStats;

    @Enumerated(EnumType.STRING)
    @NotNull private CompanyType type;

    @NotNull private Integer timesUsed;
}
