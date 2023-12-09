import {Company, conglomerate, secretObjective} from "./MatchEnums";

const hqConglomerate = {
    type: conglomerate.OMNICORP,
    isRotated: true,
};

const playerConsultants = {
    MEDIA_ADVISOR: 1,
    DEALMAKER: 0,
    CORPORATE_LAWYER: 2,
    MILITARY_CONTRACTOR: 0,
};

const opponent = {
    username: "Beluga",
    conglomeratesInHand: 4,
    hq: {
        conglomerates: [
            hqConglomerate,
            hqConglomerate,
        ],
        consultants: playerConsultants
    }
}

const handConglomerates = {
    OMNICORP: 1,
    MEGAMEDIA: 2,
    TOTAL_ENTERTAINMENT: 3,
    GENERIC_INC: 0
};

export const mockUpData = {
    companies: [
        {company: Company.WORDOFMOUTH, agents: 1, type: conglomerate.OMNICORP},
        {company: Company.SLIMGROTZ_INC, agents: 2, type: conglomerate.TOTAL_ENTERTAINMENT},
        {company: Company.VISUAL_TERROR_INC, agents: 4, type: conglomerate.MEGAMEDIA},
        {company: Company.PIX_CHIX, agents: 3, type: conglomerate.GENERIC_INC},
        {company: Company.XCURBR, agents: 1, type: conglomerate.TOTAL_ENTERTAINMENT},
        {company: Company.WALLPAPER, agents: 2, type: conglomerate.MEGAMEDIA},
        {company: Company.FLICKERING_LIGHTS, agents: 4, type: conglomerate.TOTAL_ENTERTAINMENT},
        {company: Company.LEADERBOARDER, agents: 2, type: conglomerate.GENERIC_INC},
        {company: Company.READALOT, agents: 3, type: conglomerate.MEGAMEDIA},
        {company: Company.PAGE_ONE_CORP, agents: 1, type: conglomerate.TOTAL_ENTERTAINMENT},
        {company: Company.ANONYMOUS_CROWD, agents: 4, type: conglomerate.GENERIC_INC},
        {company: Company.HOLOGRAFX, agents: 3, type: conglomerate.OMNICORP},
        {company: Company.ALL_AROUND_YOU, agents: 2, type: conglomerate.MEGAMEDIA},
        {company: Company.GENERIC_SUB_INC, agents: 2, type: conglomerate.GENERIC_INC},
        {company: Company.SUBLIMINAL_SUBSIDIARY, agents: 4, type: conglomerate.TOTAL_ENTERTAINMENT},
        {company: Company.GLOBAL_CORP, agents: 1, type: conglomerate.OMNICORP},
    ],
    player: {
        hand: handConglomerates,
        hq: {
            secretObjectives: [
                secretObjective.AMBIENT_ADVERTISING,
                secretObjective.BROADCAST_NETWORK,
            ],
            conglomerates: [
                hqConglomerate,
                hqConglomerate,
            ],
            consultants: playerConsultants
        }
    },
    generalSupply: {
        conglomeratesLeftInDeck: 30,
        openDisplay: [
            conglomerate.GENERIC_INC,
            conglomerate.MEGAMEDIA,
            conglomerate.GENERIC_INC,
            conglomerate.TOTAL_ENTERTAINMENT,
        ],
        consultants: {
            MEDIA_ADVISOR: 2,
            DEALMAKER: 3,
            CORPORATE_LAWYER: 1,
            MILITARY_CONTRACTOR: 6,
        },
    },
    opponents: [
        opponent,
        opponent,
        opponent
    ],
}
