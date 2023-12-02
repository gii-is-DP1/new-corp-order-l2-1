import {CompanyMatrix} from "./CompanyMatrix";
import {Company, conglomerate, consultant, secretObjective} from "./MatchEnums";
import Conglomerate from "./Conglomerate";
import React, {useState} from "react";
import Selector from "./Selector/Selector";
import {onlySelectOfSameColor} from "./Selector/ChangeSelectableItemsFunctions";
import {selectAtLeastOne} from "./Selector/OnConfirmFunctions";
import {ViewerContainer} from "./Viewer";
import CompanyTileSelector from "./CompanyTileSelector";
import Button from "../components/Button";

export function getCompanyImageSrc(company) {
    return "/images/companies/" + company.type + "/" + company.name + ".png";
}

export function Game() {
    const selection = [
        <Conglomerate conglomerate={conglomerate.OMNICORP}/>,
        <Conglomerate conglomerate={conglomerate.OMNICORP}/>,
        <Conglomerate conglomerate={conglomerate.GENERIC_INC}/>,
        <Conglomerate conglomerate={conglomerate.TOTAL_ENTERTAINMENT}/>,
        <Conglomerate conglomerate={conglomerate.MEGAMEDIA}/>,
        <Conglomerate conglomerate={conglomerate.MEGAMEDIA}/>,
    ];
    const conglomerateContainerStyle = {
        display: "flex",
    }

    const currentState = "Infiltrate";
    let selector;
    switch (currentState) {
        case "Infiltrate":
            selector = <Selector
                title={"Infiltrate"}
                subtitle={"Select n cards of the same color"}
                selection={selection}
                selectableElements={[...Array(selection.length).keys()]}
                canConfirm={selectAtLeastOne}
                changeSelectableItems={onlySelectOfSameColor}
                onConfirm={(selectedElements) => {
                    alert(selectedElements)
                }}
                containerStyle={conglomerateContainerStyle}
                itemStyle={{}}
            />
    }

    const hand = [...[...Array(gameState.player.hand.GENERIC_INC)].map(() => <Conglomerate
        conglomerate={conglomerate.GENERIC_INC}/>),
        ...[...Array(gameState.player.hand.MEGAMEDIA)].map(() => <Conglomerate conglomerate={conglomerate.MEGAMEDIA}/>),
        ...[...Array(gameState.player.hand.OMNICORP)].map(() => <Conglomerate conglomerate={conglomerate.OMNICORP}/>),
        ...[...Array(gameState.player.hand.TOTAL_ENTERTAINMENT)].map(() => <Conglomerate
            conglomerate={conglomerate.TOTAL_ENTERTAINMENT}/>)
    ];

    const hqConsultants = [
        ...[...Array(gameState.player.hq.consultants.DEALMAKER)].map(() => <img height={"200px"}
                                                                                src={consultant.DEALMAKER}/>),
        ...[...Array(gameState.player.hq.consultants.CORPORATE_LAWYER)].map(() => <img height={"200px"}
                                                                                       src={consultant.CORPORATE_LAWYER}/>),
        ...[...Array(gameState.player.hq.consultants.MEDIA_ADVISOR)].map(() => <img height={"200px"}
                                                                                    src={consultant.MEDIA_ADVISOR}/>),
        ...[...Array(gameState.player.hq.consultants.MILITARY_CONTRACTOR)].map(() => <img height={"200px"}
                                                                                          src={consultant.MILITARY_CONTRACTOR}/>),
    ];
    const hqConglomerates = gameState.player.hq.conglomerates.map((item) => <Conglomerate conglomerate={item.type}
                                                                                          isRotated={item.isRotated}/>);
    const secretObjectives = gameState.player.hq.secretObjectives.map((item) => <img height={"200px"} src={item}
                                                                                     alt={item}/>)
    const openDisplay = gameState.generalSupply.openDisplay.map((item) => <Conglomerate conglomerate={item}/>)

    const deck = <img height={"200px"} src={"/images/card-back.jpg"}/>

    const generalSupplyConsultants = [
        ...[...Array(gameState.generalSupply.consultants.DEALMAKER)].map(() => <img height={"200px"}
                                                                                    src={consultant.DEALMAKER}/>),
        ...[...Array(gameState.generalSupply.consultants.CORPORATE_LAWYER)].map(() => <img height={"200px"}
                                                                                           src={consultant.CORPORATE_LAWYER}/>),
        ...[...Array(gameState.generalSupply.consultants.MEDIA_ADVISOR)].map(() => <img height={"200px"}
                                                                                        src={consultant.MEDIA_ADVISOR}/>),
        ...[...Array(gameState.generalSupply.consultants.MILITARY_CONTRACTOR)].map(() => <img height={"200px"}
                                                                                              src={consultant.MILITARY_CONTRACTOR}/>),
    ];
    const [selectedCompany, setSelectedCompany] = useState(1);

    const PLOT = "plot";
    const INFILTRATE = "infiltrate";
    const TAKEOVER = "takeover";

    const [state, setState] = useState({action:null})
    const setMoveState = (action) => {
        state.action = action;
        setState({...state});
    }

    return <div style={{display:"flex"}}>
        <p>Current action: {state.action}</p>
        {state.action == null
            ? <div>
                <h1>Pick an action</h1>
                <Button onClick={() => setMoveState(PLOT)}>PLOT</Button>
                <Button onClick={() => setMoveState(INFILTRATE)}>INFILTRATE</Button>
                <Button onClick={() => setMoveState(TAKEOVER)}>TAKEOVER</Button>

            </div>
            : <></>
        }


        <div>
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
        </div>

        <ViewerContainer title="Your Hand" containerStyle={{display: "flex", flexWrap: "wrap"}}
                         buttonContent={<p>View Hand</p>}
                         items={hand}/>
        <ViewerContainer title="Your HQ" containerStyle={{display: "flex", flexWrap: "wrap"}}
                         buttonContent={<p>View Hq</p>}
                         items={[...hqConglomerates, ...hqConsultants, ...secretObjectives]}/>

        <ViewerContainer title="General Supply" containerStyle={{display: "flex", flexWrap: "wrap"}}
                         buttonContent={<p>View General Supply</p>}
                         items={[...openDisplay, deck, ...generalSupplyConsultants]}/>

        {gameState.opponents.map((opponent) => {
            const opponentHqConglomerates = opponent.hq.conglomerates.map((item) => <Conglomerate
                conglomerate={item.type} isRotated={item.isRotated}/>);
            const opponentHqConsultants = [
                ...[...Array(opponent.hq.consultants.DEALMAKER)].map(() => <img height={"200px"}
                                                                                src={consultant.DEALMAKER}/>),
                ...[...Array(opponent.hq.consultants.CORPORATE_LAWYER)].map(() => <img height={"200px"}
                                                                                       src={consultant.CORPORATE_LAWYER}/>),
                ...[...Array(opponent.hq.consultants.MEDIA_ADVISOR)].map(() => <img height={"200px"}
                                                                                    src={consultant.MEDIA_ADVISOR}/>),
                ...[...Array(opponent.hq.consultants.MILITARY_CONTRACTOR)].map(() => <img height={"200px"}
                                                                                          src={consultant.MILITARY_CONTRACTOR}/>),
            ];

            return <>
                <ViewerContainer title={opponent.username + "'s HQ"}
                                 containerStyle={{display: "flex", flexWrap: "wrap"}}
                                 buttonContent={<p>View {opponent.username}'s HQ</p>}
                                 items={[...opponentHqConglomerates, ...opponentHqConsultants]}/>

            </>
        })}
    </div>;
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
