package us.lsi.dp1.newcorporder.match.player;

import org.junit.jupiter.api.Test;
import us.lsi.dp1.newcorporder.match.ConsultantType;
import static org.assertj.core.api.Assertions.assertThat;
public class HeadquarterTest {

    @Test
    void given4DifferentConsultants_whenCalculatingConsultantVP_extraPointIsEarned() {
        Headquarter headquarter = Headquarter.create();
        headquarter.addConsultant(ConsultantType.CORPORATE_LAWYER);
        headquarter.addConsultant(ConsultantType.MEDIA_ADVISOR);
        headquarter.addConsultant(ConsultantType.MILITARY_CONTRACTOR);
        headquarter.addConsultant(ConsultantType.DEAL_MAKER);
        assertThat(headquarter.getConsultantsVP()).isEqualTo(3);
    }

    @Test
    void givenACoupleOfSameConsultants_whenCalculatingConsultantVP_coupleIsNotCounted(){
        Headquarter headquarter = Headquarter.create();
        headquarter.addConsultant(ConsultantType.CORPORATE_LAWYER);
        headquarter.addConsultant(ConsultantType.MEDIA_ADVISOR);
        headquarter.addConsultant(ConsultantType.MEDIA_ADVISOR);
        headquarter.addConsultant(ConsultantType.MEDIA_ADVISOR);
        assertThat(headquarter.getConsultantsVP()).isEqualTo(1);
    }

}
