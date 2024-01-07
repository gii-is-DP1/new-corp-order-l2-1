import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneCard} from "../../selector/pickers/Pickers";
import {Conglomerates} from "../../../components/collections/Conglomerates";


function SocialMediaConglomeratePicker() {
    const context = useContext(StateContext);
    const state = context.state;
    const conglomerates = new Conglomerates(state.takeover.ability.socialMedia.hq.rotatedConglomerates.concat(state.takeover.ability.socialMedia.hq.nonRotatedConglomerates));
    return pickOneCard(conglomerates.components, (selected) => {
        //TODO WHEN BACKEND CONNECTION
        context.update();
    })
}

export class SocialMediaConglomerateState extends FrontendState {
    component = <SocialMediaConglomeratePicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.ability.socialMedia.conglomerate !== null)
            return frontendState.DONE;
    }
}
