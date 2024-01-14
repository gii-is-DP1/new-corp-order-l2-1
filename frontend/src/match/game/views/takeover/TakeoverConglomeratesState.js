import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickManyConglomeratesOfTheSameColor} from "../../selector/pickers/Pickers";
import {Info} from "../../../Match";
import {conglomerate, getConglomerateName} from "../../../data/MatchEnums";

function TakeoverConglomeratesPicker() {
    const context = useContext(StateContext);
    const state = context.state;

    const maxConglomerates = {
        GENERIC_INC: 0,
        OMNICORP: 0,
        MEGAMEDIA: 0,
        TOTAL_ENTERTAINMENT: 0,
    }

    context.companyTiles.values.forEach(ct => {
        const name = getConglomerateName(ct.type);
        maxConglomerates[name] = Math.max(ct.agents, maxConglomerates[name]);
    })

    const selectableElements = [];
    for(let i = 0; i < context.nonrotatedHqConglomerates.values.length; i++){
        const conglomerate = getConglomerateName(context.nonrotatedHqConglomerates.values[i]);
        maxConglomerates[conglomerate]--;
        if(maxConglomerates[conglomerate] >= 0)
            selectableElements.push(i);
    }

    return pickManyConglomeratesOfTheSameColor(context.nonrotatedHqConglomerates.components, (selected) => {
        const conglomerates = selected.map(index => context.nonrotatedHqConglomerates.values[index]);
        state.takeover.conglomerates = {agents: conglomerates.length, type: conglomerates[0]}
        context.update();
    },
        selectableElements)
}

export class TakeoverConglomeratesState extends FrontendState {
    component = <TakeoverConglomeratesPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.conglomerates !== null)
            return frontendState.takeover.PICK_TWO_COMPANY_TILES;
    }
}
