import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneorTwoCards} from "../../selector/pickers/Pickers";
import {Conglomerates} from "../../../components/collections/Conglomerates";
import {RotatedConglomerateMultiset} from "../../../components/multisets/RotatedConglomerateMultiset";
import {getConglomerateName} from "../../../data/MatchEnums";
import fetchAuthenticatedWithBody from "../../../../util/fetchAuthenticatedWithBody";
import {Info} from "../../../Match";
import tokenService from "../../../../services/token.service";


function AmbientAdvertisingConglomeratesPicker() {
    const context = useContext(StateContext);
    const state = context.state;
    const info = useContext(Info);
    const conglomerates = new RotatedConglomerateMultiset(state.takeover.ability.ambientAdvertising.opponent.hq.rotatedConglomerates);
    return pickOneorTwoCards(conglomerates.components, (selected) => {
        const selectedConglomerates = selected.map(index => conglomerates.values[index]);
        //state.takeover.ability.ambientAdvertising.opponent.hq.nonRotatedConglomerates = state.takeover.ability.ambientAdvertising.opponent.hq.nonRotatedConglomerates.concat(selectedConglomerates);
        const body = {companyAbility: {type: "AmbientAdvertisingAbility",
                playerId: state.takeover.ability.ambientAdvertising.opponent.id == null ? tokenService.getUser().id : state.takeover.ability.ambientAdvertising.opponent.id,
                conglomerateToRotate: getConglomerateName(selectedConglomerates[0]),
                rotatesTwoCards: selectedConglomerates.length === 2}};
        const fetchCompany = async () => {
            try {
                await fetchAuthenticatedWithBody(`/api/v1/matches/${info.code}/turn/company-abilities`, "POST", body)
                    .then(async response => await response.json());
            } catch (error) {
                console.log(error.message)
            }
        };
        fetchCompany();
        /*for (let i = 0; i < selectedConglomerates.length; i++) {
            const index = state.takeover.ability.ambientAdvertising.opponent.hq.rotatedConglomerates.indexOf(selectedConglomerates[i]);
            state.takeover.ability.ambientAdvertising.opponent.hq.rotatedConglomerates.splice(index, 1);
        }*/
        state.takeover.ability.ambientAdvertising.conglomerates = selectedConglomerates;
        context.update();
    });
}

export class AmbientAdvertisingConglomeratesState extends FrontendState {
    component = <AmbientAdvertisingConglomeratesPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.ability.ambientAdvertising.conglomerates !== null)
            return frontendState.DONE;
    }
}

