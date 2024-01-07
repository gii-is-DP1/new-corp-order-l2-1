import {FrontendState} from "../FrontendState";
import {consultant} from "../../../data/MatchEnums";
import React from "react";

export class InfiltrateCompanyState extends FrontendState {
    component = <InfiltrateCompanyPicker/>;

    getNextState(gameState, frontendState) {
        if (gameState.infiltrate.companyTile !== null)
            return frontendState.infiltrate.PICK_CONSULTANT;
    }
}

export function InfiltrateCompanyPicker() { //TODO: implement picker
    return <>NOT IMPLEMENTED</>
}
