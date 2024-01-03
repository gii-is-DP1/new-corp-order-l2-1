import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneCard} from "../../selector/pickers/Pickers";
import {Conglomerates} from "../../../components/collections/Conglomerates";


function PrintMediaSecondConglomeratePicker() {
    const context = useContext(StateContext);
    const state = context.state;
    const conglomerates = new Conglomerates(state.takeover.ability.printMedia.otherHq.rotatedConglomerates.concat(state.takeover.ability.printMedia.otherHq.nonRotatedConglomerates));
    return pickOneCard(conglomerates.components, (selected) => {
        const index = selected[0];
        state.takeover.ability.printMedia.otherIsRotated = state.takeover.ability.printMedia.otherHq.rotatedConglomerates.length > index;
        state.takeover.ability.printMedia.otherConglomerate = conglomerates.data[index];
        //TODO MAKE BETTER BUT FOR THE TIME BEING
        if(state.takeover.ability.printMedia.yourIsRotated) {
                const index = state.game.player.hq.rotatedConglomerates.indexOf(state.takeover.ability.printMedia.yourConglomerate);
                state.game.player.hq.rotatedConglomerates.splice(index, 1);
                state.takeover.ability.printMedia.otherHq.rotatedConglomerates.push(state.takeover.ability.printMedia.yourConglomerate);
        }
        else{
            const index = state.game.player.hq.nonRotatedConglomerates.indexOf(state.takeover.ability.printMedia.yourConglomerate);
            state.game.player.hq.nonRotatedConglomerates.splice(index, 1);
            state.takeover.ability.printMedia.otherHq.nonRotatedConglomerates.push(state.takeover.ability.printMedia.yourConglomerate);
        }
        if(state.takeover.ability.printMedia.otherIsRotated)
        {
            const index = state.takeover.ability.printMedia.otherHq.rotatedConglomerates.indexOf(state.takeover.ability.printMedia.otherConglomerate);
            state.takeover.ability.printMedia.otherHq.rotatedConglomerates.splice(index, 1);
            state.game.player.hq.rotatedConglomerates.push(state.takeover.ability.printMedia.otherConglomerate);
        }
        else{
            const index = state.takeover.ability.printMedia.otherHq.nonRotatedConglomerates.indexOf(state.takeover.ability.printMedia.otherConglomerate);
            state.takeover.ability.printMedia.otherHq.nonRotatedConglomerates.splice(index, 1);
            state.game.player.hq.nonRotatedConglomerates.push(state.takeover.ability.printMedia.otherConglomerate);
        }
        context.update();
    })

}

export class PrintMediaSecondConglomerateState extends FrontendState {
    component = <PrintMediaSecondConglomeratePicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.ability.printMedia.otherConglomerate !== null)
            return frontendState.DONE;
    }
}
