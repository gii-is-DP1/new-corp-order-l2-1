import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneCard} from "../../selector/pickers/Pickers";
import {HQViewer} from "../../viewers/HQViewer";


function AmbientAdvertisingOpponentPicker() {
    const context = useContext(StateContext);
    const state = context.state;
    let players = [];
    players[0] = <p>You</p>;
    const components = context.opponents.values.map(item => <p>{item.username}</p>);
    players = players.concat(components);
    return pickOneCard(players, (selected) => {
        state.takeover.ability.ambientAdvertising.opponent = selected[0] === 0 ? state.game.player  : context.opponents.values[selected[0]-1];
        context.update();
    })

}

export class AmbientAdvertisingOpponentState extends FrontendState {
    component = <AmbientAdvertisingOpponentPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.ability.ambientAdvertising.opponent !== null)
            return frontendState.takeover.AMBIENT_ADVERTISING_PICK_CONGLOMERATES;
    }
}
