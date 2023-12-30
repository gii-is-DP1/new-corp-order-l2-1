import {FrontendState} from "./FrontendState";
import React, {useContext} from "react";
import {StateContext} from "../Game";
import Button, {ButtonType} from "../../../components/Button";
import {INFILTRATE, PLOT, TAKEOVER} from "../../data/MatchEnums";
import {conglomerateContainerStyle, pickOneCard} from "../selector/pickers/Pickers";
import {selectAtLeastOne} from "../selector/CanConfirmFunctions";
import {selectQuantity} from "../selector/ChangeSelectableItemsFunctions";
import Selector from "../selector/Selector";

function ActionChooser() { //TODO: use picker to substitute this method
    const context = useContext(StateContext);
    const setMoveState = (action) => {
        context.state.action = action;
        context.update();
    }

    const selection = [ //TODO: make these like cards
        <Button buttonType={ButtonType.primary} action = {PLOT}>PLOT</Button>,
        <Button buttonType={ButtonType.primary} action = {INFILTRATE}>INFILTRATE</Button>,
        <Button buttonType={ButtonType.primary} action = {TAKEOVER}>TAKEOVER</Button>,
    ]

    const onConfirm = (selectedItems) => {
        const selectedItemIndex = selectedItems[0];
        const selectedComponent = selection[selectedItemIndex];
        const action = selectedComponent.props.action;
        setMoveState(action);
    }

    return <Selector title={"What will you do?"}
                     selection={selection}
                     canConfirm={selectAtLeastOne}
                     changeSelectableItems={selectQuantity(1)}
                     onConfirm={onConfirm}
                     containerStyle={{display: "flex", justifyContent: "space-around"}}
                     key={onConfirm}/>
}


export class ChooseActionState extends FrontendState {
    component = <ActionChooser/>

    getNextState(gameState, frontendState) {
        switch (gameState.action) {
            case PLOT:
                return frontendState.plot.DRAW_FIRST_CONGLOMERATE;
            case INFILTRATE:
                return frontendState.infiltrate.PICK_CONGLOMERATES
            case TAKEOVER:
                return frontendState.takeover.PICK_CONSULTANT;
        }
    }
}
