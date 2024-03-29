package us.lsi.dp1.newcorporder.match.player;

import com.google.common.base.Preconditions;
import com.google.common.collect.*;
import lombok.*;
import us.lsi.dp1.newcorporder.match.company.CompanyType;
import us.lsi.dp1.newcorporder.match.conglomerate.Conglomerate;
import us.lsi.dp1.newcorporder.match.consultant.ConsultantType;
import us.lsi.dp1.newcorporder.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@AllArgsConstructor
@Builder
@EqualsAndHashCode(of = "playerId")
public class MatchPlayer {

    public static MatchPlayer create(Player player) {
        return MatchPlayer.builder()
            .playerId(player.getId())
            .username(player.getUser().getUsername())
            .picture(player.getUser().getPicture())
            .secretObjectives(new ArrayList<>(2))
            .headquarter(Headquarter.create())
            .build();
    }

    @Getter private final Integer playerId;
    @Getter private final String username;
    @Getter private final Integer picture;
    @Getter @Setter private boolean online;

    private final Multiset<Conglomerate> hand = HashMultiset.create();
    private final List<CompanyType> secretObjectives;
    @Getter private final Headquarter headquarter;

    @Getter private int timesPlotted;
    @Getter private int timesInfiltrated;
    @Getter private int timesTakenOver;
    @Getter private final Multiset<CompanyType> abilityUses = HashMultiset.create();
    @Getter private final Multiset<Conglomerate> shareUses = HashMultiset.create();
    @Getter private final Multiset<Conglomerate> agentUses = HashMultiset.create();
    @Getter private final Multiset<ConsultantType> consultantUses = HashMultiset.create();

    public void init(ConsultantType initialConsultant, List<Conglomerate> initialHand) {
        this.hand.addAll(initialHand);
        this.headquarter.addConsultant(initialConsultant);
        this.selectSecretObjectives();
    }

    private void selectSecretObjectives() {
        List<CompanyType> companyTypes = Lists.newArrayList(CompanyType.values());
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            CompanyType selectedType = companyTypes.get(random.nextInt(companyTypes.size()));
            this.secretObjectives.add(selectedType);
            companyTypes.remove(selectedType);
        }
    }

    public void addShareToHand(Conglomerate conglomerate) {
        this.addSharesToHand(conglomerate, 1);
    }

    public void addSharesToHand(Conglomerate conglomerate, int amount) {
        this.hand.add(conglomerate, amount);
    }

    public void discardSharesFromHand(Conglomerate conglomerate, int sharesToDiscard) {
        Preconditions.checkState(this.hand.count(conglomerate) >= sharesToDiscard,
            "you don't have enough shares of the given conglomerate to discard");

        this.hand.remove(conglomerate, sharesToDiscard);
    }

    public Multiset<Conglomerate> getHand() {
        return ImmutableMultiset.copyOf(hand);
    }

    public List<CompanyType> getSecretObjectives() {
        return ImmutableList.copyOf(secretObjectives);
    }

    public int getParticipationPoints(Conglomerate conglomerateType) {
        return headquarter.getTotalConglomeratesShares(conglomerateType) +
               (headquarter.getAgentsCaptured(conglomerateType) * 2);
    }

    public void addTimePlotted() {
        this.timesPlotted++;
    }

    public void addTimeInfiltrated() {
        this.timesInfiltrated++;
    }

    public void addTimeTakenOver() {
        this.timesTakenOver++;
    }

    public void addAbilityUse(CompanyType type) {
        this.abilityUses.add(type);
    }

    public void addShareUses(Conglomerate conglomerate, int uses) {
        this.shareUses.add(conglomerate, uses);
    }

    public void addAgentUses(Conglomerate conglomerate, int uses) {
        this.agentUses.add(conglomerate);
    }

    public void addConsultantUse(ConsultantType type) {
        this.consultantUses.add(type);
    }
}
