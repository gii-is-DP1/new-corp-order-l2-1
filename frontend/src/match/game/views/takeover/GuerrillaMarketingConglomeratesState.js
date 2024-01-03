import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneorTwoCards} from "../../selector/pickers/Pickers";
import {Conglomerates} from "../../../components/collections/Conglomerates";


function GuerrillaMarketingConglomeratesPicker() {
    const context = useContext(StateContext);
    const state = context.state;
    const conglomerates = new Conglomerates(state.takeover.ability.guerrillaMarketing.opponent.hq.nonRotatedConglomerates)
    return pickOneorTwoCards(conglomerates.components, (selected) => {
        const selectedConglomerates = selected.map(index => state.takeover.ability.guerrillaMarketing.opponent.hq.nonRotatedConglomerates[index]);
        state.takeover.ability.guerrillaMarketing.opponent.hq.rotatedConglomerates = state.takeover.ability.guerrillaMarketing.opponent.hq.rotatedConglomerates.concat(selectedConglomerates);
        for (let i = 0; i < selectedConglomerates.length; i++) {
            const index = state.takeover.ability.guerrillaMarketing.opponent.hq.nonRotatedConglomerates.indexOf(selectedConglomerates[i]);
            state.takeover.ability.guerrillaMarketing.opponent.hq.nonRotatedConglomerates.splice(index, 1);
        }
        state.takeover.ability.guerrillaMarketing.conglomerates = selected.map(index => state.takeover.ability.guerrillaMarketing.opponent.hq.nonRotatedConglomerates[index]);
        context.update();
    });
}

export class GuerrillaMarketingConglomeratesState extends FrontendState {
    component = <GuerrillaMarketingConglomeratesPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.ability.guerrillaMarketing.conglomerates !== null)
            return frontendState.DONE;
    }
}

