import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneCard} from "../../selector/pickers/Pickers";
import {HQViewer} from "../../viewers/HQViewer";


function SocialMediaHqPicker() {
    const context = useContext(StateContext);
    const state = context.state;
    let players = [];
    players[0] = <HQViewer/>;
    players = players.concat(context.opponents.components);
    return pickOneCard(players, (selected) => {
        state.takeover.ability.socialMedia.hq = selected[0] === 0 ? state.game.player  : context.opponents.values[selected[0]-1];
        context.update();
    })

}

export class SocialMediaHqState extends FrontendState {
    component = <SocialMediaHqPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.ability.socialMedia.hq !== null)
            return frontendState.takeover.SOCIAL_MEDIA_PICK_CONGLOMERATE;
    }
}
