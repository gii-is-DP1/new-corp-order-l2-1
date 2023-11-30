import {CompanyMatrix} from "./CompanyMatrix";
import {Company, CompanyType, conglomerate} from "./MatchEnums";
import CompanyTileSelector from "./CompanyTileSelector";
import {useState} from "react";

export function getCompanyImageSrc(company) {
    return "/images/companies/" + company.type + "/" + company.name + ".png";
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


export function Game() {
    const [selectedCompany, setSelectedCompany] = useState(-1);
    return <>
        <p>GAME IN PROGRESS</p>
        <CompanyMatrix companyTiles={gameState.companies}/>
        {selectedCompany === -1
            ? <CompanyTileSelector companies={gameState.companies}
                                   selectableCompanies={[...Array(16).keys()]}
                                   title="Select a company tile"
                                   onConfirmSelection={(i) => setSelectedCompany(i)}
                                   needsDoubleSelect={true}
            />
            : <p> You selected {gameState.companies[selectedCompany].company.name}</p>
        }
    </>;
}

const companyTiles = [];
