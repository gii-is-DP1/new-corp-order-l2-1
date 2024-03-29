import {Company, conglomerate, secretObjective} from "./MatchEnums";

const playerConsultants = {
    MEDIA_ADVISOR: 1,
    DEALMAKER: 1,
    CORPORATE_LAWYER: 1,
    MILITARY_CONTRACTOR: 1,
};

const opponent = {
    id: 1,
    username: "Beluga",
    conglomeratesInHand: 4,
    hq: {
        rotatedConglomerates: [
            conglomerate.OMNICORP,
            conglomerate.MEGAMEDIA,
        ],
        nonRotatedConglomerates: [
            conglomerate.OMNICORP,
            conglomerate.TOTAL_ENTERTAINMENT,
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

export function getDefaultState() {
    return {
        game: mockUpData,
        turn: 0,
        discardedConglomerates: null,
        isPlaying: false,
        action: null,
        plot: {
            firstConglomerate: null,
            secondConglomerate: null,
        },
        infiltrate: {
            conglomerate: null,
            conglomerateQuantity: null,
            companyTile: null,
            consultant: null,
            mediaAdvisor: {
                conglomerate: null,
            },
            corporateLawyer: {
                conglomerates: null,
                company: null,
            },
            takenConsultant: null
        },
        takeover: {
            consultant: null,
            conglomerates: null,
            companyTiles: null,
            canActivateCompanyAbility: false,
            ability: {
                choice: null,
                broadcastNetwork: {
                    companies: null,
                },
                guerrillaMarketing: {
                    opponent: null,
                    conglomerates: null,
                },
                printMedia: {
                    yourConglomerate: null,
                    yourIsRotated: null,
                    opponent: null,
                    otherConglomerate: null,
                    otherIsRotated: null,
                },
                ambientAdvertising: {
                    opponent: null,
                    conglomerates: null,
                },
                socialMedia: {
                    opponent: null,
                    conglomerate: null,
                },
                onlineMarketing: {
                    companies: null,
                }
            },
            dealmaker: {
                conglomerates: null,
            }
        }
    }
}

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
            rotatedConglomerates: [
                conglomerate.OMNICORP,
                conglomerate.MEGAMEDIA,
            ],
            nonRotatedConglomerates: [
                conglomerate.OMNICORP,
                conglomerate.TOTAL_ENTERTAINMENT,
            ],
            consultants: playerConsultants
        }
    },
    generalSupply: {
        conglomeratesLeftInDeck: 30,
        openDisplay: {
            GENERIC_INC: 1,
            MEGAMEDIA: 2,
            OMNICORP: 1,
            TOTAL_ENTERTAINMENT: 1,
        },
        consultants: {
            MEDIA_ADVISOR: 2,
            DEALMAKER:
                3,
            CORPORATE_LAWYER:
                1,
            MILITARY_CONTRACTOR:
                6,
        }
        ,
    },
    opponents: [
        opponent,
        opponent,
        opponent
    ],
}

export const defaultMatchInfo = {
    code: null,
    isAdmin: false,
    inLobby: false,
    hasBeenKicked: false,
    isSpectating: true,
    maxPlayers: 4,
    players: [],
    isWinner: null
};
