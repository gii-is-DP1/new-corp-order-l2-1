package us.lsi.dp1.newcorporder.match.company;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

public class CompanyMatrix {

    /**
     * Builds a new matrix for the given size.
     *
     * @param matrixSize the size of the new matrix
     * @return the new matrix
     */
    public static CompanyMatrix build(CompanyMatrixSize matrixSize) {
        CompanyTile[] matrix = new CompanyTile[matrixSize == CompanyMatrixSize.COUPLE ? 12 : 16];

        List<Company> companies = Lists.newArrayList(Company.values()); // using Guava's Lists to get a mutable list
        Collections.shuffle(companies);

        // if the matrix is for a couple game, keep only 2 companies for each company type
        if (matrixSize == CompanyMatrixSize.COUPLE) {
            Multiset<CompanyType> companyTypeCount = HashMultiset.create();
            companies.removeIf(company -> companyTypeCount.add(company.getType(), 1) >= 2);
        }

        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = new CompanyTile(companies.get(i));
        }
        return new CompanyMatrix(matrixSize, matrix);
    }

    @Getter private final CompanyMatrixSize matrixSize;
    private final CompanyTile[] tiles;

    private CompanyMatrix(CompanyMatrixSize matrixSize, CompanyTile[] tiles) {
        this.matrixSize = matrixSize;
        this.tiles = tiles;
    }

    /**
     * Gets the tile at position (x, y).
     *
     * @param x the x position in the matrix
     * @param y the y position in the matrix
     * @return the tile at the given position
     */
    public CompanyTile getTile(int x, int y) {
        Preconditions.checkArgument(x < this.matrixSize.getRows(), "matrix has only %d rows (x = %d)", this.matrixSize.getRows(), x);
        Preconditions.checkArgument(y < this.matrixSize.getColumns(), "matrix has only %d columns (y = %d)", this.matrixSize.getColumns(), y);

        return tiles[x * this.matrixSize.getColumns() + y];
    }
}
