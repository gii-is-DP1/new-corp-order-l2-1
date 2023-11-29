package us.lsi.dp1.newcorporder.match.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.MatchSize;

import static org.assertj.core.api.Assertions.assertThat;

class CompanyMatrixTest {

    CompanyMatrix companyMatrix;

    @BeforeEach
    void setUp() {
        companyMatrix = CompanyMatrix.create();
    }

    @ParameterizedTest
    @EnumSource(CompanyType.class)
    void givenCoupleMatchSize_whenInitializingCompanyMatrix_matrixSizeIs4x3AndThereAreTwoCompaniesOfEachConglomerate(CompanyType companyType) {
        companyMatrix.init(MatchSize.COUPLE);

        assertThat(companyMatrix.getTiles())
            .hasSize(4 * 3)
            .doesNotHaveDuplicates();

        assertThat(companyMatrix.getTiles())
            .filteredOn(tile -> tile.getCompany().getType(), companyType)
            .hasSizeLessThanOrEqualTo(2);
    }

    @Test
    void givenGroupMatchSize_whenInitializingCompanyMatrix_matrixSizeIs4x4() {
        companyMatrix.init(MatchSize.GROUP);

        assertThat(companyMatrix.getTiles())
            .hasSize(4 * 4)
            .doesNotHaveDuplicates();
    }

    @ParameterizedTest
    @EnumSource(Conglomerate.class)
    void whenInitializingCompanyMatrix_agentsAreDistributedEquitably(Conglomerate conglomerate) {
        companyMatrix.init(MatchSize.GROUP);

        assertThat(companyMatrix.getTiles())
            .filteredOn(CompanyTile::getCurrentConglomerate, conglomerate)
            .hasSize(companyMatrix.getTiles().length / Conglomerate.values().length);
    }
}
