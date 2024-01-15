import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneCard} from "../../selector/pickers/Pickers";
import {HQViewer} from "../../viewers/HQViewer";
import {components} from "react-big-calendar";


function SocialMediaHqPicker() {
    const context = useContext(StateContext);
    const state = context.state;
    let players = [];
    const components = context.opponents.values.map(item => <p>{item.username}</p>);
    players[0] = <p>You</p>;
    players = players.concat(components);
    return pickOneCard(players, (selected) => {
        state.takeover.ability.socialMedia.opponent = selected[0] === 0 ? state.game.player  : context.opponents.values[selected[0]-1];
        context.update();
    })

}

export class SocialMediaHqState extends FrontendState {
    component = <SocialMediaHqPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.ability.socialMedia.opponent !== null)
            return frontendState.takeover.SOCIAL_MEDIA_PICK_CONGLOMERATE;
    }
}
