import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneCard} from "../../selector/pickers/Pickers";
import {Conglomerates} from "../../../components/collections/Conglomerates";
import {ConglomerateMultiset} from "../../../components/multisets/ConglomerateMultiset";


function PrintMediaFirstConglomeratePicker() {
    const context = useContext(StateContext);
    const state = context.state;
    const components =  [...context.rotatedHqConglomerates.components,...context.nonrotatedHqConglomerates.components];
    const values =  [...context.rotatedHqConglomerates.values,...context.nonrotatedHqConglomerates.values];
    return pickOneCard(components, (selected) => {
        const index = selected[0];
        state.takeover.ability.printMedia.yourIsRotated = context.rotatedHqConglomerates.values.length > index;
        state.takeover.ability.printMedia.yourConglomerate = values[index];
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
