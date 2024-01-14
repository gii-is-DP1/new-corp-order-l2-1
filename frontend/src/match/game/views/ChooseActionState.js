import {FrontendState} from "./FrontendState";
import React, {useContext} from "react";
import {StateContext} from "../Game";
import Button, {ButtonType} from "../../../components/Button";
import {getConsultantName, INFILTRATE, PLOT, TAKEOVER} from "../../data/MatchEnums";
import {conglomerateContainerStyle, pickOneCard} from "../selector/pickers/Pickers";
import {selectAtLeastOne} from "../selector/CanConfirmFunctions";
import {selectQuantity} from "../selector/ChangeSelectableItemsFunctions";
import Selector from "../selector/Selector";
import css from "../game.module.css";
import fetchAuthenticated from "../../../util/fetchAuthenticated";
import {Info} from "../../Match";

function ActionChooser() { //TODO: use picker to substitute this method
    const context = useContext(StateContext);
    const info = useContext(Info);
    const setMoveState = (action) => {
        context.state.action = action;
        context.update();
    }

    const selection = [ //TODO: make these like cards
        <img className={css.actionCard} src={"/images/plot.png"} alt={PLOT} action={PLOT}/>,
        <img className={css.actionCard} src={"/images/infiltrate.png"} alt={INFILTRATE} action={INFILTRATE}/>,
        <img className={css.actionCard} src={"/images/takeover.png"} alt={TAKEOVER} action={TAKEOVER}/>,
    ]

    const onConfirm = (selectedItems) => {
        const selectedItemIndex = selectedItems[0];
        const selectedComponent = selection[selectedItemIndex];
        const action = selectedComponent.props.action;
        setMoveState(action);

        switch (action)
        {
            case INFILTRATE:
                const fetchAction = async () => {
                    try {
                        await fetchAuthenticated(`/api/v1/matches/${info.code}/turn?action=INFILTRATE`, "POST");
                    } catch (error) {
                        console.log(error.message)
                    }
                };
                fetchAction();
        }
    }

    const selectableElements = [0];
    if(context.hand.values.length > 0)
        selectableElements.push(1);
    if(context.nonrotatedHqConglomerates.values.length > 0)
        selectableElements.push(2);

    return <Selector title={"What will you do?"}
                     help={
                         <>
                             <h3>Plot</h3>
                             <p>Take Conglomerate cards into your hand </p>
                             <h3>Infiltrate</h3>
                             <p>Place Conglomerate cards from your hand into your HQ to place Agents on Company
                                 tiles</p>
                             <h3>Takeover</h3>
                             <p>Rotate Conglomerate cards in your HQ to move Agents</p>
                         </>
                     }
                     selection={selection}
                     canConfirm={selectAtLeastOne}
                     changeSelectableItems={selectQuantity(1)}
                     onConfirm={onConfirm}
                     containerStyle={
                         {
                             display: "flex", justifyContent:
                                 "space-around"
                         }
                     }
                     selectableElements={selectableElements}
                     key={onConfirm}
    />
}


export class ChooseActionState extends FrontendState {
    component = <ActionChooser/>

    getNextState(gameState, frontendState) {
        switch (gameState.action) {
            case PLOT:
                return frontendState.plot.DRAW_FIRST_CONGLOMERATE;
            case INFILTRATE:
                return frontendState.infiltrate.PICK_CONSULTANT;
            case TAKEOVER:
                return frontendState.takeover.PICK_CONSULTANT;
        }
    }
}
