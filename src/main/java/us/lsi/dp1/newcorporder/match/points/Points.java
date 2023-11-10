package us.lsi.dp1.newcorporder.match.points;

import org.jetbrains.annotations.NotNull;
import us.lsi.dp1.newcorporder.match.Conglomerate;
import us.lsi.dp1.newcorporder.match.company.Company;
import us.lsi.dp1.newcorporder.stats.CompanyPlayerMatchStats;
import us.lsi.dp1.newcorporder.stats.ConglomeratePlayerMatchStats;
import us.lsi.dp1.newcorporder.stats.ConsultantPlayerMatchStats;
import us.lsi.dp1.newcorporder.stats.PlayerMatchStats;

import java.util.*;

public class Points {

    private List<PlayerMatchStats> playerMatchStats; // Lista de estadísticas de jugadores

    public Points(List<PlayerMatchStats> playerMatchStats) {
        this.playerMatchStats = playerMatchStats;
    }

    public void calculatePoints() {
        for (PlayerMatchStats playerStats : playerMatchStats) {
            int totalPoints = 0;

            // Calcular puntos por acciones de Conglomerate y agentes en HQ
            totalPoints += calculateConglomeratePoints(playerStats);

            // Calcular puntos por mayoría y segunda mayoría de acciones
            totalPoints += calculateMajorityPoints(playerStats);

            // Calcular puntos por objetivos secretos
            totalPoints += calculateSecretObjectivePoints(playerStats);

            // Calcular puntos por consultores
            totalPoints += calculateConsultantPoints(playerStats);

            // Asignar los puntos totales al jugador
            playerStats.setTotalVP(totalPoints);
        }
    }

    private int calculateConglomeratePoints(@NotNull PlayerMatchStats playerStats) {
        int conglomeratePoints = 0;
        Set<ConglomeratePlayerMatchStats> conglomerateStats = playerStats.getConglomerateStats();

        for (ConglomeratePlayerMatchStats stats : conglomerateStats) {
            int shares = stats.getShares();
            int agents = stats.getAgents();
            int conglomerateVP = shares + (agents * 2);
            conglomeratePoints += conglomerateVP;
        }

        return conglomeratePoints;
    }

    private int calculateMajorityPoints(@NotNull PlayerMatchStats playerStats) {
        int majorityPoints = 0;

        Set<ConglomeratePlayerMatchStats> conglomerateStats = playerStats.getConglomerateStats();
        Map<Conglomerate, Integer> sharesByConglomerate = new HashMap<>();

        // Calcular la cantidad de acciones de cada conglomerado
        for (ConglomeratePlayerMatchStats conglomerateStat : conglomerateStats) {
            sharesByConglomerate.put(conglomerateStat.getConglomerate(), conglomerateStat.getShares());
        }

        // Encontrar el conglomerado con la mayoría de acciones
        Conglomerate majorityConglomerate = null;
        int majorityShares = -1;

        for (Map.Entry<Conglomerate, Integer> entry : sharesByConglomerate.entrySet()) {
            if (entry.getValue() > majorityShares) {
                majorityConglomerate = entry.getKey();
                majorityShares = entry.getValue();
            }
        }

        // Calcular los puntos de la mayoría
        for (Map.Entry<Conglomerate, Integer> entry : sharesByConglomerate.entrySet()) {
            if (entry.getKey().equals(majorityConglomerate)) {
                majorityPoints += entry.getValue() * 2; // 2 VP por cada share en la mayoría
            }
        }

        // Encontrar el conglomerado con la segunda mayoría de acciones
        Conglomerate secondMajorityConglomerate = null;
        int secondMajorityShares = -1;

        for (Map.Entry<Conglomerate, Integer> entry : sharesByConglomerate.entrySet()) {
            if (!entry.getKey().equals(majorityConglomerate) && entry.getValue() > secondMajorityShares) {
                secondMajorityConglomerate = entry.getKey();
                secondMajorityShares = entry.getValue();
            }
        }

        // Calcular los puntos de la segunda mayoría
        for (Map.Entry<Conglomerate, Integer> entry : sharesByConglomerate.entrySet()) {
            if (entry.getKey().equals(secondMajorityConglomerate)) {
                majorityPoints += entry.getValue(); // 1 VP por cada share en la segunda mayoría
            }
        }

        return majorityPoints;
    }

    private int calculateSecretObjectivePoints(@NotNull PlayerMatchStats playerStats) {
        int secretObjectivePoints = 0;
        Set<String> secretObjectives = playerStats.getSecretObjectives();
        Set<CompanyPlayerMatchStats> companyStats = playerStats.getCompanyStats();
        Set<ConglomeratePlayerMatchStats> conglomerateStats = playerStats.getConglomerateStats();

        // Crear un mapa que relacione cada Company con el Conglomerate que la controla
        Map<Company, Conglomerate> controlledCompanies = new HashMap<>();
        for (CompanyPlayerMatchStats companyStat : companyStats) {
            controlledCompanies.put(companyStat.getCompanyType(), companyStat.getControllingConglomerate());
        }

        // Crear un conjunto que contenga los Conglomerates por los que el jugador ha puntuado
        Set<Conglomerate> playerPuntuadoConglomerates = new HashSet<>();
        for (ConglomeratePlayerMatchStats conglomerateStat : conglomerateStats) {
            if (conglomerateStat.getShares() > 0) {
                playerPuntuadoConglomerates.add(conglomerateStat.getConglomerate());
            }
        }

        // Iterar a través de los objetivos secretos
        for (String secretObjective : secretObjectives) {
            for (Company company : controlledCompanies.keySet()) {
                Conglomerate controllingConglomerate = controlledCompanies.get(company);

                // Comprobar si el Conglomerate que controla la Company coincide con un Conglomerate
                // por el que el jugador ha puntuado (tiene acciones)
                if (playerPuntuadoConglomerates.contains(controllingConglomerate)) {
                    secretObjectivePoints += 2; // 2 VP por cada Company controlada por un Conglomerate puntuado
                    break; // Si una Company es controlada por un Conglomerate, no es necesario verificar más
                }
            }
        }

        return secretObjectivePoints;
    }

    private int calculateConsultantPoints(@org.jetbrains.annotations.NotNull PlayerMatchStats playerStats) {
        int consultantPoints = 0;
        Set<ConsultantPlayerMatchStats> consultantStats = playerStats.getConsultantStats();

        int totalDifferentConsultants = consultantStats.size();

        // Contar cuántos consultores distintos tiene el jugador
        if (totalDifferentConsultants >= 2) {
            consultantPoints += totalDifferentConsultants / 2; // 1 VP por cada par de consultores diferentes

            if (totalDifferentConsultants == 4) {
                consultantPoints += 1; // 1 VP extra si tiene los 4 consultores distintos
            }
        }

        return consultantPoints;
    }

}
