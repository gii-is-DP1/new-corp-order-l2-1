import {CompanyMatrix} from "./CompanyMatrix";
import {Company, CompanyType, conglomerate} from "./MatchEnums";

export function getCompanyImageSrc(company) {
    return "/images/companies/" + company.type + "/" + company.name + ".png";
}

export function Game() {
    const companyTiles = [
        Company.GLOBAL_CORP,
        Company.WORDOFMOUTH,
        Company.SLIMGROTZ_INC,
        Company.VISUAL_TERROR_INC,
        Company.SUBLIMINAL_SUBSIDIARY,
        Company.GENERIC_SUB_INC,
        Company.ALL_AROUND_YOU,
        Company.HOLOGRAFX,
        Company.ANONYMOUS_CROWD,
        Company.PAGE_ONE_CORP,
        Company.READALOT,
        Company.LEADERBOARDER,
        Company.FLICKERING_LIGHTS,
        Company.WALLPAPER,
        Company.XCURBR,
        Company.PIX_CHIX,
    ];

    return <>
        <p>GAME IN PROGRESS</p>
        <CompanyMatrix companyTiles={companyTiles}/>
    </>;
}

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
    TOTAL_ENTERTAINMENT: 4,
    GENERIC_INC: 9
};


const gameState = {
    companies: [{company: Company.READALOT, agents: 1, type: conglomerate.OMNICORP}],
    player: {
        hand: handConglomerates,
        secretObjectives: [
            CompanyType.AMBIENT_ADVERTISING,
            CompanyType.BROADCAST_NETWORK
        ],
        consultants: playerConsultants,
    },
    generalSupply: {
        conglomeratesLeftInDeck: 30,
        openDisplay: [
            conglomerate.GENERIC_INC,
            conglomerate.MEGAMEDIA,
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
