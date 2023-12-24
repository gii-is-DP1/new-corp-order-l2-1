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
} from "../MatchEnums";
import Conglomerate from "../Conglomerate";
import React, {useState} from "react";
import {ViewerContainer} from "../Viewer";
import Button from "../../components/Button";
import {Consultant} from "../Consultant";
import Deck from "../Deck";
import {HandViewer} from "./viewer/HandViewer";
import {HQViewer} from "./viewer/HQViewer";
import {GeneralSupplyViewer} from "./viewer/GeneralSupplyViewer";
import {
    pickCompany, pickConglomeratesToDiscard, pickConglomerateToDiscard,
    pickManyConglomeratesOfTheSameColor,
    pickOneCard,
    pickOneOrZeroCards,
    pickOrthogonallyAdjacentCompanyTiles
} from "../Pickers";
import {CompanyTile} from "../companyTiles/CompanyTile";
import {ConglomerateMultiset} from "../multisets/ConglomerateMultiset";
import {mockUpData} from "../MockupData";
import {selectOrthogonallyAdjacentTiles} from "./selector/ChangeSelectableItemsFunctions";
import Selector from "./selector/Selector";
import {ConsultantMultiset} from "../multisets/ConsultantMultiset";

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
        if (state.infiltrate.consultant >= 3 && state.infiltrate.takenConsultant === null)
            return frontendState.infiltrate.TAKE_CONSULTANT;
    } else if (state.action === TAKEOVER) {
        if (state.takeover.consultant === null && state.takeover.consultant !== -1)
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
    if (hand.values.length > 6)
        return frontendState.DISCARD;
    return frontendState.DONE;
}

let hand;

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
            takenConsultant: null
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
    const [canActivateCompanyAbility, setCanActivateCompanyAbility] = useState(false);
    hand = new ConglomerateMultiset(state.game.player.hand);
    const hqConsultants = new ConsultantMultiset(state.game.player.hq.consultants);

    const hqConglomerates = getHqConglomerates(state);
    const generalSupplyConsultants = getConsultants(state.game.generalSupply.consultants);
    const secretObjectives = getSecretObjectives(state);
    const openDisplay = getOpenDisplay(state);
    const currentAction = getCurrentAction(state, canActivateCompanyAbility);
    const view = getFrontendView(currentAction, state, setState);
    return <div style={{display: "flex"}}>
        {view}
        <div>
            <HandViewer items={hand.components}/>
            <HQViewer hqItems={[...hqConglomerates, ...hqConsultants.components, ...secretObjectives]}/>
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

function getCompanyComponentArray(types) {
    return types.map((type) => <CompanyTile company={type}/>)
}

function getSecretObjectives(state) {
    return state.game.player.hq.secretObjectives.map((item) =>
        <img height={"200px"} src={item} alt="secret objective"/>)
}

function getOpenDisplay(state) {
    return state.game.generalSupply.openDisplay.map((item) => <Conglomerate conglomerate={item}/>)
}

function viewOpponent(opponent) {
    const opponentHqConglomerates = opponent.hq.conglomerates.map((item) => <Conglomerate
        conglomerate={item.type} isRotated={item.isRotated}/>);
    const opponentHqConsultants = [
        ...[...Array(opponent.hq.consultants.DEALMAKER)].map(() => <Consultant src={consultant.DEALMAKER}/>),
        ...[...Array(opponent.hq.consultants.CORPORATE_LAWYER)].map(() => <Consultant
            src={consultant.CORPORATE_LAWYER}/>),
        ...[...Array(opponent.hq.consultants.MEDIA_ADVISOR)].map(() => <Consultant src={consultant.MEDIA_ADVISOR}/>),
        ...[...Array(opponent.hq.consultants.MILITARY_CONTRACTOR)].map(() => <Consultant
            src={consultant.MILITARY_CONTRACTOR}/>),
    ];

    return <>
        <ViewerContainer title={opponent.username + "'s HQ"}
                         containerStyle={{display: "flex", flexWrap: "wrap"}}
                         buttonContent={<p>View {opponent.username}'s HQ</p>}
                         items={[...opponentHqConglomerates, ...opponentHqConsultants]}/>
    </>
}

function chooseAction(setMoveState) {
    return <div>
        <h1>Pick an action</h1>
        <Button onClick={() => setMoveState(PLOT)}>PLOT</Button>
        <Button onClick={() => setMoveState(INFILTRATE)}>INFILTRATE</Button>
        <Button onClick={() => setMoveState(TAKEOVER)}>TAKEOVER</Button>
    </div>
}

/*
function actionPicker()
{
    return <Selector selection={}/>;
}
*/
function getFrontendView(currentAction, state, setState) {
    const openDisplay = getOpenDisplay(state);
    const openDisplayAndDeck = [...openDisplay, <Deck/>];
    const setMoveState = (action) => {
        state.action = action;
        setState({...state});
    }
    const generalSupplyConsultants = new ConsultantMultiset(state.game.generalSupply);
    const yourConsultants = new ConsultantMultiset(state.game.player.hq.consultants);
    switch (currentAction) {
        case frontendState.plot.DRAW_FIRST_CONGLOMERATE: //TODO: remove card from general supply
            return pickOneCard(openDisplayAndDeck, selected => {
                state.plot.firstConglomerate = openDisplayAndDeck[selected[0]];
                setState({...state});
            })
        case frontendState.plot.DRAW_SECOND_CONGLOMERATE:
            return pickOneCard(openDisplayAndDeck, (selected) => {
                state.plot.secondConglomerate = openDisplayAndDeck[selected[0]];
                setState({...state});
            })
        case frontendState.infiltrate.PICK_CONGLOMERATES:
            return pickManyConglomeratesOfTheSameColor(hand.componentArray, (selected) => {
                state.infiltrate.conglomerates = selected.map(index => hand.valueArray[index]);
                setState({...state});
            })
        case frontendState.infiltrate.PICK_COMPANY:
            return pickCompany(getCompanyComponentArray(state.game.companies), (selected) => {
                state.infiltrate.companyTile = state.game.companies[selected[0]];
                setState({...state});
            }, 1)
        case frontendState.infiltrate.PICK_CONSULTANT:
            return pickOneCard(yourConsultants.components, (selected) => {
                const index = selected[0];
                state.infiltrate.consultant = yourConsultants.values[index];
                setState({...state});
            })

        case frontendState.infiltrate.MEDIA_ADVISOR_PICK_CONGLOMERATE:
            return pickOneCard(hand.componentArray, (selected) => {
                state.infiltrate.mediaAdvisor.conglomerate = hand.valueArray[selected[0]];
                setState({...state});
            })
        case frontendState.infiltrate.CORPORATE_LAWYER_PICK_CONGLOMERATES:
            return pickManyConglomeratesOfTheSameColor(hand.componentArray, (selected) => {
                state.infiltrate.corporateLawyer.conglomerates = hand.valueArray[selected[0]];
                setState({...state});
            })
        case frontendState.infiltrate.CORPORATE_LAWYER_PICK_COMPANY:
            return pickCompany(getCompanyComponentArray(state.game.companies.filter(t => t !== state.infiltrate.companyTile)), (selected) => {
                state.infiltrate.corporateLawyer.company = state.game.companies[selected[0]];
                setState({...state});
            }, 1)
        case frontendState.infiltrate.TAKE_CONSULTANT:
            return pickOneCard(generalSupplyConsultants.components, (selected) => {
                const index = selected[0];
                state.infiltrate.consultant = generalSupplyConsultants.values[index];
                setState({...state});
            })
        case frontendState.takeover.PICK_CONSULTANT:
            return pickOneOrZeroCards(yourConsultants.components, (selected) => {
                if (selected[0] == null)
                    state.takeover.consultant = -1;
                else {
                    const index = selected[0];
                    state.takeover.consultant = yourConsultants.values[index];
                }
                setState({...state});
            })
        case frontendState.takeover.PICK_CONGLOMERATES:
            return pickManyConglomeratesOfTheSameColor(hand.componentArray, (selected) => {
                state.takeover.conglomerates = selected.map(index => hand.valueArray[index]);
                setState({...state});
            })
        case frontendState.takeover.PICK_TWO_COMPANY_TILES:
            return pickOrthogonallyAdjacentCompanyTiles(getCompanyComponentArray(state.game.companies), (selected) => {
                state.takeover.companyTiles = selected.map(index => state.game.companies[index]);
                setState({...state});
            })
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
            return pickConglomeratesToDiscard(hand.componentArray, (selected) => {
                //TODO: backend connection
            })
        case frontendState.DONE:
            return <></>
        case frontendState.CHOOSE_ACTION:
            return chooseAction(setMoveState);
    }
}
