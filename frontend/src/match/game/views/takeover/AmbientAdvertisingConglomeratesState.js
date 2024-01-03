import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneorTwoCards} from "../../selector/pickers/Pickers";
import {Conglomerates} from "../../../components/collections/Conglomerates";


function AmbientAdvertisingConglomeratesPicker() {
    const context = useContext(StateContext);
    const state = context.state;
    const conglomerates = new Conglomerates(state.takeover.ability.guerrillaMarketing.opponent.hq.rotatedConglomerates)
    return <p></p>/*
    return pickOneorTwoCards(conglomerates.components, (selected) => {
        const selectedConglomerates = selected.map(index => state.takeover.ability.guerrillaMarketing.opponent.hq.nonRotatedConglomerates[index]);
        state.takeover.ability.guerrillaMarketing.opponent.hq.rotatedConglomerates = state.takeover.ability.guerrillaMarketing.opponent.hq.rotatedConglomerates.concat(selectedConglomerates);
        for (let i = 0; i < selectedConglomerates.length; i++) {
            const index = state.takeover.ability.guerrillaMarketing.opponent.hq.nonRotatedConglomerates.indexOf(selectedConglomerates[i]);
            state.takeover.ability.guerrillaMarketing.opponent.hq.nonRotatedConglomerates.splice(index, 1);
        }
        state.takeover.ability.guerrillaMarketing.conglomerates = selected.map(index => state.takeover.ability.guerrillaMarketing.opponent.hq.nonRotatedConglomerates[index]);
        context.update();
    });*/
}

export class AmbientAdvertisingConglomeratesState extends FrontendState {
    component = <AmbientAdvertisingConglomeratesPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.ability.ambientAdvertising.conglomerates !== null)
            return frontendState.DONE;
    }
}

