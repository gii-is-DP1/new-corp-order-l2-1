import {FrontendState} from "../FrontendState";
import {consultant} from "../../../data/MatchEnums";
import React, {useContext} from "react";
import {pickCompany} from "../../selector/pickers/Pickers";
import {StateContext} from "../../Game";

export class InfiltrateCompanyState extends FrontendState {
    component = <InfiltrateCompanyPicker/>;

    getNextState(gameState, frontendState) {
        if (gameState.infiltrate.companyTile !== null)
            return frontendState.infiltrate.PICK_CONSULTANT;
    }
}

export function InfiltrateCompanyPicker() { //TODO: implement picker
    const context = useContext(StateContext);
    return pickCompany(context.companyTiles.components, selected => {
        context.state.infiltrate.companyTile = selected[0];
        context.update();
    }, 1);
}
