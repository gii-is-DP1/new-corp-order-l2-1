import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneCard} from "../../selector/pickers/Pickers";
import {Conglomerates} from "../../../components/collections/Conglomerates";


function PrintMediaFirstConglomeratePicker() {
    const context = useContext(StateContext);
    const state = context.state;
    const conglomerates = new Conglomerates(state.game.player.hq.rotatedConglomerates.concat(state.game.player.hq.nonRotatedConglomerates));
    return pickOneCard(conglomerates.components, (selected) => {
        const index = selected[0];
        state.takeover.ability.printMedia.yourIsRotated = state.game.player.hq.rotatedConglomerates.length > index;
        state.takeover.ability.printMedia.yourConglomerate = conglomerates.values[index];
        context.update();
    })

}

export class PrintMediaFirstConglomerateState extends FrontendState {
    component = <PrintMediaFirstConglomeratePicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.ability.printMedia.yourConglomerate !== null)
            return frontendState.takeover.PRINT_MEDIA_PICK_OTHER_HQ;
    }
}
