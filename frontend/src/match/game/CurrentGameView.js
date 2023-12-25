import Deck from "../components/Deck";
import {frontendState, INFILTRATE, PLOT, TAKEOVER} from "../data/MatchEnums";
import {
    pickCompany,
    pickConglomeratesToDiscard,
    pickManyConglomeratesOfTheSameColor,
    pickOneCard,
    pickOneOrZeroCards,
    pickOrthogonallyAdjacentCompanyTiles
} from "./selector/pickers/Pickers";
import React, {useContext} from "react";
import Button from "../../components/Button";
import {StateContext} from "./Game";

export const CurrentGameView = () => {
    const context= useContext(StateContext);
    const state = context.state;
    const hand = context.hand;

    switch (context.frontendState) {
        case frontendState.plot.DRAW_FIRST_CONGLOMERATE: //TODO: remove card from general supply
            return <PlotFirstConglomeratePicker/>
        case frontendState.plot.DRAW_SECOND_CONGLOMERATE:
            return <PlotSecondConglomeratePicker/>
        case frontendState.infiltrate.PICK_CONGLOMERATES:
            return pickManyConglomeratesOfTheSameColor(context.hand.components, (selected) => {
                state.infiltrate.conglomerates = selected.map(index => context.hand.values[index]);
                context.update();
            })
        case frontendState.infiltrate.PICK_COMPANY:
            return pickCompany(context.companyTiles.components, (selected) => {
                state.infiltrate.companyTile = state.game.companies[selected[0]];
                context.update();
            }, 1)
        case frontendState.infiltrate.PICK_CONSULTANT:
            return pickOneCard(context.playerConsultants.components, (selected) => {
                const index = selected[0];
                state.infiltrate.consultant = context.playerConsultants.values[index];
                context.update();
            })
        case frontendState.infiltrate.MEDIA_ADVISOR_PICK_CONGLOMERATE:
            return pickOneCard(context.hand.components, (selected) => {
                state.infiltrate.mediaAdvisor.conglomerate = context.hand.values[selected[0]];
                context.update();
            })
        case frontendState.infiltrate.CORPORATE_LAWYER_PICK_CONGLOMERATES:
            return pickManyConglomeratesOfTheSameColor(context.hand.components, (selected) => {
                state.infiltrate.corporateLawyer.conglomerates = context.hand.values[selected[0]];
                context.update();
            })
        case frontendState.infiltrate.CORPORATE_LAWYER_PICK_COMPANY:
            return pickCompany(context.companyTiles.components, (selected) => {
                state.infiltrate.corporateLawyer.company = state.game.companies[selected[0]];
                context.update();
            }, 1)
        case frontendState.infiltrate.TAKE_CONSULTANT:
            return pickOneCard(state.generalSupplyConsultants.components, (selected) => {
                const index = selected[0];
                state.infiltrate.consultant = state.generalSupplyConsultants.values[index];
                context.update();
            })
        case frontendState.takeover.PICK_CONSULTANT:
            return pickOneOrZeroCards(state.playerConsultants.components, (selected) => {
                if (selected[0] == null)
                    state.takeover.consultant = -1;
                else {
                    const index = selected[0];
                    state.takeover.consultant = state.playerConsultants.values[index];
                }
                context.update();
            })
        case frontendState.takeover.PICK_CONGLOMERATES:
            return pickManyConglomeratesOfTheSameColor(context.hand.components, (selected) => {
                state.takeover.conglomerates = selected.map(index => context.hand.values[index]);
                context.update();
            })
        case frontendState.takeover.PICK_TWO_COMPANY_TILES:
            return pickOrthogonallyAdjacentCompanyTiles(context.companyTiles(state.game.companies), (selected) => {
                state.takeover.companyTiles = selected.map(index => state.game.companies[index]);
                context.update();
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
            return pickConglomeratesToDiscard(hand.components, (selected) => {
                //TODO: backend connection
            })
        case frontendState.DONE:
            return <p>Done</p>
        case frontendState.CHOOSE_ACTION:
            return <ActionChooser/> //chooseAction(setMoveState);
    }
}

function ActionChooser() { //TODO: use picker to substitute this method
    const context= useContext(StateContext);
    const setMoveState = (action) => {
        context.state.action = action;
        context.update();
    }
    const Option = ({action}) => <Button onClick={() => setMoveState(action)}>{action.toUpperCase()}</Button>

    return <div>
        <h1>Pick an action</h1>
        <Option action={PLOT}/>
        <Option action={INFILTRATE}/>
        <Option action={TAKEOVER}/>
    </div>
}


function PlotFirstConglomeratePicker(){
    return DrawConglomerate(true);
}
function PlotSecondConglomeratePicker(){
    return DrawConglomerate(false);
}
function DrawConglomerate(isFirst){
    const context= useContext(StateContext);
    const openDisplayAndDeck = [...context.openDisplay.components, <Deck/>];

    return pickOneCard(openDisplayAndDeck, selected => {
        const selectedConglomerate = openDisplayAndDeck[selected[0]];

        if(isFirst)
            context.state.plot.firstConglomerate = selectedConglomerate
        else
            context.state.plot.secondConglomerate = selectedConglomerate
        context.update();
    })
}
