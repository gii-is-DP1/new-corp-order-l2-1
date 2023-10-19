package us.lsi.dp1.newcorporder.match.company;

import lombok.Getter;

@Getter
public enum CompanyMatrixSize {

    COUPLE(4, 3),
    GROUP(4, 4);

    private final int rows, columns;

    CompanyMatrixSize(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
    }
}
