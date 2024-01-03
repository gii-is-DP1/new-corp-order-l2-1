import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {OpponentConglomerateViewer} from "../../viewers/OpponentConglomerateViewer";
import {pickOneCard, pickOneorTwoCard, pickOneorTwoCards} from "../../selector/pickers/Pickers";
import {Conglomerates} from "../../../components/collections/Conglomerates";


function GuerrillaMarketingConglomeratesPicker() {
    const context = useContext(StateContext);
    const state = context.state;
    //console.log(state.takeover.ability.guerrillaMarketing.opponent.props.opponent.hq.nonRotatedConglomerates);
    const conglomerates = new Conglomerates(state.takeover.ability.guerrillaMarketing.opponent.props.opponent.hq.nonRotatedConglomerates)
    return pickOneorTwoCards(conglomerates.components, (selected) => {

    });
}
    /*const context = useContext(StateContext);
    const state = context.state;
    const opponents = [];
    for (let i = 0; i < state.game.opponents.length; i++) {
        opponents[i] = <OpponentConglomerateViewer opponent={state.game.opponents[i]} key={i}/>;
    }
    return pickOneCard(opponents, (selected) => {
        console.log(opponents);
    })
*/

export class GuerrillaMarketingConglomeratesState extends FrontendState {
    component = <GuerrillaMarketingConglomeratesPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.ability.guerrillaMarketing.conglomerates !== null)
            return frontendState.DONE;
    }
}

