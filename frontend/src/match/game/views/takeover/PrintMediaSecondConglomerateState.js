import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneCard} from "../../selector/pickers/Pickers";
import {Conglomerates} from "../../../components/collections/Conglomerates";
import {getConglomerateName} from "../../../data/MatchEnums";
import fetchAuthenticatedWithBody from "../../../../util/fetchAuthenticatedWithBody";
import {Info} from "../../../Match";
import {ConglomerateMultiset} from "../../../components/multisets/ConglomerateMultiset";


function PrintMediaSecondConglomeratePicker() {
    const context = useContext(StateContext);
    const info = useContext(Info);
    const state = context.state;
    const components =  [...new ConglomerateMultiset(state.takeover.ability.printMedia.opponent.hq.rotatedConglomerates).components,...new ConglomerateMultiset(state.takeover.ability.printMedia.opponent.hq.nonRotatedConglomerate).components];
    const values = [...new ConglomerateMultiset(state.takeover.ability.printMedia.opponent.hq.rotatedConglomerates).values,...new ConglomerateMultiset(state.takeover.ability.printMedia.opponent.hq.nonRotatedConglomerate).values];;
    return pickOneCard(components, (selected) => {
        const index = selected[0];
        state.takeover.ability.printMedia.otherIsRotated = state.takeover.ability.printMedia.opponent.hq.rotatedConglomerates.length > index;
        state.takeover.ability.printMedia.otherConglomerate = values[index];
        const body = {companyAbility: {type: "PrintMediaAbility",
                playerId: state.takeover.ability.printMedia.opponent.id,
                ownConglomerate: getConglomerateName(state.takeover.ability.printMedia.yourConglomerate),
                isOwnRotated: state.takeover.ability.printMedia.yourIsRotated,
                otherConglomerate: getConglomerateName(state.takeover.ability.printMedia.otherConglomerate),
                isOtherRotated: state.takeover.ability.printMedia.otherIsRotated}};
        const fetchCompany = async () => {
            try {
                await fetchAuthenticatedWithBody(`/api/v1/matches/${info.code}/turn/company-abilities`, "POST", body)
                    .then(async response => await response.json());
            } catch (error) {
                console.log(error.message)
            }
        };
        fetchCompany();
        /*
        if(state.takeover.ability.printMedia.yourIsRotated) {
                const index = state.game.player.hq.rotatedConglomerates.indexOf(state.takeover.ability.printMedia.yourConglomerate);
                state.game.player.hq.rotatedConglomerates.splice(index, 1);
                state.takeover.ability.printMedia.opponent.hq.rotatedConglomerates.push(state.takeover.ability.printMedia.yourConglomerate);
        }
        else{
            const index = state.game.player.hq.nonRotatedConglomerates.indexOf(state.takeover.ability.printMedia.yourConglomerate);
            state.game.player.hq.nonRotatedConglomerates.splice(index, 1);
            state.takeover.ability.printMedia.opponent.hq.nonRotatedConglomerates.push(state.takeover.ability.printMedia.yourConglomerate);
        }
        if(state.takeover.ability.printMedia.otherIsRotated)
        {
            const index = state.takeover.ability.printMedia.opponent.hq.rotatedConglomerates.indexOf(state.takeover.ability.printMedia.otherConglomerate);
            state.takeover.ability.printMedia.opponent.hq.rotatedConglomerates.splice(index, 1);
            state.game.player.hq.rotatedConglomerates.push(state.takeover.ability.printMedia.otherConglomerate);
        }
        else{
            const index = state.takeover.ability.printMedia.opponent.hq.nonRotatedConglomerates.indexOf(state.takeover.ability.printMedia.otherConglomerate);
            state.takeover.ability.printMedia.opponent.hq.nonRotatedConglomerates.splice(index, 1);
            state.game.player.hq.nonRotatedConglomerates.push(state.takeover.ability.printMedia.otherConglomerate);
        }*/
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
