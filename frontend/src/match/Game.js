import {
    Company,
    CompanyType,
    conglomerate,
    consultant,
    frontendState,
    INFILTRATE,
    PLOT,
    secretObjective,
    TAKEOVER
} from "./MatchEnums";
import Conglomerate from "./Conglomerate";
import React, {useState} from "react";
import Selector from "./Selector/Selector";
import {onlySelectOfSameColor, selectQuantity} from "./Selector/ChangeSelectableItemsFunctions";
import {selectAtLeastOne} from "./Selector/OnConfirmFunctions";
import {ViewerContainer} from "./Viewer";
import Button from "../components/Button";
import {Consultant} from "./Consultant";
import Deck from "./Deck";
import {HandViewer} from "./Viewers/HandViewer";
import {HQViewer} from "./Viewers/HQViewer";
import {GeneralSupplyViewer} from "./Viewers/GeneralSupplyViewer";

export function getCompanyImageSrc(company) {
    return "/images/companies/" + company.type + "/" + company.name + ".png";
}

function getCurrentAction(state, canActivateCompanyAbility) {
    if (state.action === null)
        return frontendState.CHOOSE_ACTION;
    if (state.action === PLOT) {
        if (state.plot.firstConglomerate === null)
            return frontendState.plot.DRAW_FIRST_CONGLOMERATE;
        if (state.plot.secondConglomerate === null)
            return frontendState.plot.DRAW_SECOND_CONGLOMERATE;
    } else if (state.action === INFILTRATE) {
        if (state.infiltrate.conglomerates === null)
            return frontendState.infiltrate.PICK_CONGLOMERATES;
        if (state.infiltrate.companyTile === null)
            return frontendState.infiltrate.PICK_COMPANY;
        if (state.infiltrate.consultant === null)
            return frontendState.infiltrate.PICK_CONSULTANT
        if (state.infiltrate.consultant === consultant.MEDIA_ADVISOR)
            return frontendState.infiltrate.MEDIA_ADVISOR_PICK_CONGLOMERATE;
        if (state.infiltrate.consultant === consultant.CORPORATE_LAWYER) {
            if (state.infiltrate.corporateLawyer.conglomerates === null)
                return frontendState.infiltrate.CORPORATE_LAWYER_PICK_CONGLOMERATES;
            if (state.infiltrate.corporateLawyer.company === null)
                return frontendState.infiltrate.CORPORATE_LAWYER_PICK_COMPANY;
        }
    } else if (state.action === TAKEOVER) {
        if (state.takeover.consultant === null)
            return frontendState.takeover.PICK_CONSULTANT;
        if (state.takeover.conglomerates === null)
            return frontendState.takeover.PICK_CONGLOMERATES;
        if (state.takeover.companyTiles === null)
            return frontendState.takeover.PICK_TWO_COMPANY_TILES;
        if (state.takeover.consultant === consultant.DEALMAKER)
            return frontendState.takeover.DEALMAKER_DRAW_TWO_CARDS_FROM_DECK;
        if (state.takeover.ability.choice === null && canActivateCompanyAbility)
            return frontendState.takeover.ACTIVATE_ABILITY;
        if (state.takeover.ability.choice === CompanyType.BROADCAST_NETWORK && state.takeover.ability.broadcastNetwork.companies === null)
            return frontendState.BROADCAST_NETWORK_PICK_COMPANIES;
        if (state.takeover.ability.choice === CompanyType.AMBIENT_ADVERTISING && state.takeover.ability.ambientAdvertising.conglomerates === null)
            return frontendState.AMBIENT_ADVERTISING_PICK_CONGLOMERATES;
        if (state.takeover.ability.choice === CompanyType.SOCIAL_MEDIA && state.takeover.ability.socialMedia.conglomerate === null)
            return frontendState.SOCIAL_MEDIA_PICK_CONGLOMERATE;
        if (state.takeover.ability.choice === CompanyType.PRINT_MEDIA && state.takeover.ability.printMedia.yourConglomerate === null)
            return frontendState.PRINT_MEDIA_PICK_YOUR_CONGLOMERATE;
        if (state.takeover.ability.choice === CompanyType.PRINT_MEDIA && state.takeover.ability.printMedia.otherConglomerate === null)
            return frontendState.PRINT_MEDIA_PICK_OTHER_CONGLOMERATE;
        if (state.takeover.ability.choice === CompanyType.GUERRILLA_MARKETING && state.takeover.ability.guerrillaMarketing.conglomerates === null)
            return frontendState.GUERRILLA_MARKETING_PICK_CONGLOMERATES;
        if (state.takeover.ability.choice === CompanyType.ONLINE_MARKETING && state.takeover.ability.onlineMarketing.companies === null)
            return frontendState.ONLINE_MARKETING_PICK_COMPANIES;
    }
    if (state.game.player.hand.OMNICORP +
        state.game.player.hand.MEGAMEDIA +
        state.game.player.hand.TOTAL_ENTERTAINMENT +
        state.game.player.hand.GENERIC_INC
        > 6
    ) //TODO hand count
        return frontendState.DISCARD;
    return frontendState.DONE;
}

const conglomerateContainerStyle = {
    display: "flex",
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

const mockUpData = {
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

export function Game() {
    const [state, setState] = useState({
        game: mockUpData,
        action: null,
        plot: {
            firstConglomerate: null,
            secondConglomerate: null,
        },
        infiltrate: {
            conglomerates: null,
            companyTile: null,
            consultant: null,
            mediaAdvisor: {
                conglomerate: null,
            },
            corporateLawyer: {
                conglomerates: null,
                company: null,
            },
            takeConsultant: null
        },
        takeover: {
            consultant: null,
            conglomerates: null,
            companyTiles: null,
            ability: {
                choice: null,
                broadcastNetwork: {
                    companies: null,
                },
                guerrillaMarketing: {
                    conglomerates: null,
                },
                printMedia: {
                    yourConglomerate: null,
                    otherHq: null,
                    otherConglomerate: null,
                },
                ambientAdvertising: {
                    conglomerates: null,
                },
                socialMedia: {
                    hq: null,
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
    })
    console.log(state.game);
    const [canActivateCompanyAbility, setCanActivateCompanyAbility] = useState(false);

    const hqConglomerates = getHqConglomerates(state);
    const hqConsultants = getConsultants(state.game.player.hq.consultants);
    const generalSupplyConsultants = getConsultants(state.game.generalSupply.consultants);
    const secretObjectives = getSecretObjectives(state);
    const openDisplay = getOpenDisplay(state);
    const hand = getHand(state);
    const currentAction = getCurrentAction(state, canActivateCompanyAbility);
    const view = getFrontendView(currentAction, state, setState);
    return <div style={{display: "flex"}}>
        {view}
        <p>Current action: {state.action}</p>

        <div>
            <HandViewer items={hand}/>
            <HQViewer hqItems={[...hqConglomerates, ...hqConsultants, ...secretObjectives]}/>
            <GeneralSupplyViewer items={[...openDisplay, <Deck/>, ...generalSupplyConsultants]}/>
            {state.game.opponents.map(viewOpponent)}
        </div>
    </div>;
}

function getHqConglomerates(state) {
    return state.game.player.hq.conglomerates.map((item) =>
        <Conglomerate conglomerate={item.type} isRotated={item.isRotated}/>);
}

function getConsultants(consultants) {
    return [
        ...[...Array(consultants.DEALMAKER)].map(() => <Consultant consultant={consultant.DEALMAKER}/>),
        ...[...Array(consultants.CORPORATE_LAWYER)].map(() => <Consultant
            consultant={consultant.CORPORATE_LAWYER}/>),
        ...[...Array(consultants.MEDIA_ADVISOR)].map(() => <Consultant consultant={consultant.MEDIA_ADVISOR}/>),
        ...[...Array(consultants.MILITARY_CONTRACTOR)].map(() => <Consultant
            consultant={consultant.MILITARY_CONTRACTOR}/>)
    ];
}

function getSecretObjectives(state) {
    return state.game.player.hq.secretObjectives.map((item) =>
        <img height={"200px"} src={item} alt="secret objective"/>)
}

function getHand(state) {
    const hand = state.game.player.hand;
    return [...[...Array(hand.GENERIC_INC)].map(() => <Conglomerate
        conglomerate={conglomerate.GENERIC_INC}/>),
        ...[...Array(hand.MEGAMEDIA)].map(() => <Conglomerate conglomerate={conglomerate.MEGAMEDIA}/>),
        ...[...Array(hand.OMNICORP)].map(() => <Conglomerate conglomerate={conglomerate.OMNICORP}/>),
        ...[...Array(hand.TOTAL_ENTERTAINMENT)].map(() => <Conglomerate
            conglomerate={conglomerate.TOTAL_ENTERTAINMENT}/>)
    ];
}
function getOpenDisplay(state){
    return state.game.generalSupply.openDisplay.map((item) => <Conglomerate conglomerate={item}/>)
}

function viewOpponent(opponent) {
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
}

function pickOneCard(from, onConfirm) {
    return <Selector title={"Pick a conglomerate"}
                     selection={from}
                     canConfirm={selectAtLeastOne}
                     changeSelectableItems={selectQuantity(1)}
                     onConfirm={onConfirm}
                     containerStyle={conglomerateContainerStyle}
    />
}

function pickManyConglomeratesOfTheSameColor(from, onConfirm) {
    return <Selector title={"Pick a conglomerate"}
                     selection={from}
                     canConfirm={onlySelectOfSameColor}
                     changeSelectableItems={selectAtLeastOne}
                     onConfirm={onConfirm}
                     containerStyle={conglomerateContainerStyle}
    />
}

function chooseAction(setMoveState) {
    return <div>
        <h1>Pick an action</h1>
        <Button onClick={() => setMoveState(PLOT)}>PLOT</Button>
        <Button onClick={() => setMoveState(INFILTRATE)}>INFILTRATE</Button>
        <Button onClick={() => setMoveState(TAKEOVER)}>TAKEOVER</Button>
    </div>
}

function getFrontendView(currentAction, state, setState) {
    const openDisplay = getOpenDisplay(state);
    const openDisplayAndDeck = [...openDisplay, <Deck/>];
    const hand = getHand(state);

    const setMoveState = (action) => {
        state.action = action;
        setState({...state});
    }

    switch (currentAction) {
        case frontendState.plot.DRAW_FIRST_CONGLOMERATE: //TODO: remove card from general supply
            return pickOneCard([...openDisplayAndDeck], (selected) => {
                state.plot.firstConglomerate = openDisplayAndDeck[selected[0]];
                setState({...state});
            });
        case frontendState.plot.DRAW_SECOND_CONGLOMERATE:
            return pickOneCard([...openDisplayAndDeck], (selected) => {
                state.plot.secondConglomerate = openDisplayAndDeck[selected[0]];
                setState({...state});
            });
        case frontendState.infiltrate.PICK_CONGLOMERATES:
            return pickManyConglomeratesOfTheSameColor(hand, (selected) => {
                state.infiltrate.conglomerates = selected.map((item) => hand[item]);
                setState({...state});
            });
        case frontendState.infiltrate.PICK_CONSULTANT:
            return pickOneCard(state.pa);
        case frontendState.infiltrate.MEDIA_ADVISOR_PICK_CONGLOMERATE:
        case frontendState.infiltrate.CORPORATE_LAWYER_PICK_CONGLOMERATES:
        case frontendState.infiltrate.CORPORATE_LAWYER_PICK_COMPANY:
        case frontendState.infiltrate.PICK_COMPANY:
        case frontendState.infiltrate.TAKE_CONSULTANT:
        case frontendState.takeover.PICK_CONSULTANT:
        case frontendState.takeover.PICK_CONGLOMERATES:
        case frontendState.takeover.PICK_TWO_COMPANY_TILES:
        case frontendState.takeover.ACTIVATE_ABILITY:
        case frontendState.takeover.DEALMAKER_DRAW_TWO_CARDS_FROM_DECK:
        case frontendState.BROADCAST_NETWORK_PICK_COMPANIES:
        case frontendState.GUERRILLA_MARKETING_PICK_CONGLOMERATES:
        case frontendState.PRINT_MEDIA_PICK_YOUR_CONGLOMERATE:
        case frontendState.PRINT_MEDIA_PICK_OTHER_CONGLOMERATE:
        case frontendState.AMBIENT_ADVERTISING_PICK_CONGLOMERATES:
        case frontendState.SOCIAL_MEDIA_PICK_CONGLOMERATE:
        case frontendState.ONLINE_MARKETING_PICK_COMPANIES:
        case frontendState.DISCARD:
        case frontendState.DONE:
        case frontendState.CHOOSE_ACTION:
            return chooseAction(setMoveState);
    }
}
