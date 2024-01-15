import {FrontendState} from "../FrontendState";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneCard} from "../../selector/pickers/Pickers";
import {Conglomerates} from "../../../components/collections/Conglomerates";
import {getConglomerateName} from "../../../data/MatchEnums";
import fetchAuthenticatedWithBody from "../../../../util/fetchAuthenticatedWithBody";
import {RotatedConglomerateMultiset} from "../../../components/multisets/RotatedConglomerateMultiset";
import {ConglomerateMultiset} from "../../../components/multisets/ConglomerateMultiset";
import {Info} from "../../../Match";
import tokenService from "../../../../services/token.service";


function SocialMediaConglomeratePicker() {
    const context = useContext(StateContext);
    const state = context.state;
    const info = useContext(Info);
    const values = [...new RotatedConglomerateMultiset(state.takeover.ability.socialMedia.opponent.hq.rotatedConglomerates).values,...new ConglomerateMultiset(state.takeover.ability.socialMedia.opponent.hq.nonRotatedConglomerates).values];
    const components =  [...(new RotatedConglomerateMultiset(state.takeover.ability.socialMedia.opponent.hq.rotatedConglomerates).components),...(new ConglomerateMultiset(state.takeover.ability.socialMedia.opponent.hq.nonRotatedConglomerates).components)];
    return pickOneCard(components, (selected) => {
        state.takeover.ability.socialMedia.conglomerate = values[selected[0]];
        const body = {companyAbility: {type: "SocialMediaAbility",
                playerId: state.takeover.ability.socialMedia.opponent.id == null ? tokenService.getUser().id : state.takeover.ability.socialMedia.opponent.id,
                conglomerateToRemove: getConglomerateName(values[selected[0]])}};
        const fetchCompany = async () => {
            try {
                await fetchAuthenticatedWithBody(`/api/v1/matches/${info.code}/turn/company-abilities`, "POST", body)
                    .then(async response => await response.json());
            } catch (error) {
                console.log(error.message)
            }
        };
        fetchCompany();
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
