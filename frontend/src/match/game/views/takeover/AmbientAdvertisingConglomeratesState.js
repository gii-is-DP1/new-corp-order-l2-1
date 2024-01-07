import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneorTwoCards} from "../../selector/pickers/Pickers";
import {Conglomerates} from "../../../components/collections/Conglomerates";


function AmbientAdvertisingConglomeratesPicker() {
    const context = useContext(StateContext);
    const state = context.state;
    const conglomerates = new Conglomerates(state.takeover.ability.ambientAdvertising.opponent.hq.rotatedConglomerates);
    return pickOneorTwoCards(conglomerates.components, (selected) => {
        const selectedConglomerates = selected.map(index => state.takeover.ability.ambientAdvertising.opponent.hq.rotatedConglomerates[index]);
        state.takeover.ability.ambientAdvertising.opponent.hq.nonRotatedConglomerates = state.takeover.ability.ambientAdvertising.opponent.hq.nonRotatedConglomerates.concat(selectedConglomerates);
        for (let i = 0; i < selectedConglomerates.length; i++) {
            const index = state.takeover.ability.ambientAdvertising.opponent.hq.rotatedConglomerates.indexOf(selectedConglomerates[i]);
            state.takeover.ability.ambientAdvertising.opponent.hq.rotatedConglomerates.splice(index, 1);
        }
        state.takeover.ability.ambientAdvertising.conglomerates = selectedConglomerates;
        context.update();
    });
}

export class AmbientAdvertisingConglomeratesState extends FrontendState {
    component = <AmbientAdvertisingConglomeratesPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.ability.ambientAdvertising.conglomerates !== null)
            return frontendState.DONE;
    }
}

