import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneorTwoCards} from "../../selector/pickers/Pickers";
import fetchAuthenticatedWithBody from "../../../../util/fetchAuthenticatedWithBody";
import {Info} from "../../../Match";
import {ConglomerateMultiset} from "../../../components/multisets/ConglomerateMultiset";
import {getConglomerateName} from "../../../data/MatchEnums";


function GuerrillaMarketingConglomeratesPicker() {
    const context = useContext(StateContext);
    const info = useContext(Info);
    const state = context.state;
    const conglomerates = new ConglomerateMultiset(state.takeover.ability.guerrillaMarketing.opponent.hq.nonRotatedConglomerates)
    return pickOneorTwoCards(conglomerates.components, (selected) => {
        const selectedConglomerates = selected.map(index => conglomerates.values[index]);
        const body = {companyAbility: {type: "GuerrillaMarketingAbility",
                playerId: state.takeover.ability.guerrillaMarketing.opponent.id,
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
        /*state.takeover.ability.guerrillaMarketing.opponent.hq.rotatedConglomerates = state.takeover.ability.guerrillaMarketing.opponent.hq.rotatedConglomerates.concat(selectedConglomerates);
        for (let i = 0; i < selectedConglomerates.length; i++) {
            const index = state.takeover.ability.guerrillaMarketing.opponent.hq.nonRotatedConglomerates.indexOf(selectedConglomerates[i]);
            state.takeover.ability.guerrillaMarketing.opponent.hq.nonRotatedConglomerates.splice(index, 1);
        }*/
        state.takeover.ability.guerrillaMarketing.conglomerates = selectedConglomerates;
            context.update();
        });
}

export class GuerrillaMarketingConglomeratesState extends FrontendState {
    component = <GuerrillaMarketingConglomeratesPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.takeover.ability.guerrillaMarketing.conglomerates !== null)
            return frontendState.DONE;
    }
}

