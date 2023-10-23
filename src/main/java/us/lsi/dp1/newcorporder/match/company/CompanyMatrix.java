package us.lsi.dp1.newcorporder.match.company;

import com.google.common.base.Preconditions;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import lombok.Getter;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.MatchSize;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CompanyMatrix {

    /**
     * Builds a new matrix.
     *
     * @return the new matrix
     */
    public static CompanyMatrix create() {
        return new CompanyMatrix();
    }

    @Getter private MatchSize matchSize;
    private CompanyTile[] tiles;

    private CompanyMatrix() {
    }

    public void init(MatchSize matchSize) {
        CompanyTile[] tiles = new CompanyTile[matchSize == MatchSize.COUPLE ? 12 : 16];

        List<Company> companies = createCompanies(matchSize);
        List<Conglomerate> agents = createAgents(matchSize);

        // fill the matrix with the companies in random order and a random agent on every tile
        for (int i = 0; i < tiles.length; i++) {
            tiles[i] = new CompanyTile(companies.get(i), agents.get(i));
        }

        this.matchSize = matchSize;
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
        Preconditions.checkArgument(x < this.matchSize.getRows(), "matrix has only %d rows (x = %d)", this.matchSize.getRows(), x);
        Preconditions.checkArgument(y < this.matchSize.getColumns(), "matrix has only %d columns (y = %d)", this.matchSize.getColumns(), y);

        return tiles[x * this.matchSize.getColumns() + y];
    }

    private static List<Company> createCompanies(MatchSize matchSize) {
        List<Company> companies = Lists.newArrayList(Company.values()); // using Guava's Lists to get a mutable list
        Collections.shuffle(companies);

        // if the matrix is for a couple game, keep only 2 companies for each company type
        if (matchSize == MatchSize.COUPLE) {
            Multiset<CompanyType> companyTypeCount = HashMultiset.create();
            companies.removeIf(company -> companyTypeCount.add(company.getType(), 1) >= 2);
        }

        return companies;
    }

    private static List<Conglomerate> createAgents(MatchSize matchSize) {
        int agentsPerConglomerate = matchSize.getColumns() * matchSize.getRows() / Conglomerate.values().length;

        List<Conglomerate> agents = new ArrayList<>();
        for (Conglomerate conglomerate : Conglomerate.values()) {
            for (int i = 0; i < agentsPerConglomerate; i++) {
                agents.add(conglomerate);
            }
        }

        Collections.shuffle(agents);
        return agents;
    }
}
