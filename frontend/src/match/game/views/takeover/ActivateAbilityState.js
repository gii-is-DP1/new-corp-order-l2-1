import {FrontendState} from "../FrontendState";
import {CompanyType, consultant} from "../../../data/MatchEnums";
import React, {useContext} from "react";
import {StateContext} from "../../Game";
import {pickOneCard} from "../../selector/pickers/Pickers";
import Button, {ButtonType} from "../../../../components/Button";
import fetchAuthenticatedWithBody from "../../../../util/fetchAuthenticatedWithBody";
import {Info} from "../../../Match";

function AbilityChooser() { //TODO:implement boolean choice
    const context= useContext(StateContext);
    const info = useContext(Info);
    const state = context.state;
    const confirmButton = [];
    confirmButton[0] = <Button buttonType={ButtonType.primary} value={true}>
        <p>ACTIVATE ABILITY</p>
    </Button>;
    confirmButton[1] = <Button buttonType={ButtonType.primary} value={false}>
            <p>DON'T ACTIVATE ABILITY</p>
        </Button>;


    return pickOneCard(confirmButton, (selected) => {
        const choice = confirmButton[selected[0]].props.value;
        if(choice === true)
            state.takeover.ability.choice = state.takeover.companyTiles[1].company.type;
        else {
            state.takeover.ability.choice = choice;
            const body = {companyAbility: null};
            const fetchCompany = async () => {
                try {
                    await fetchAuthenticatedWithBody(`/api/v1/matches/${info.code}/turn/company-abilities`, "POST", body)
                        .then(async response => await response.json());
                } catch (error) {
                    console.log(error.message)
                }
            };
            fetchCompany();
        }
        context.update();
    })
}

export class ActivateAbilityState extends FrontendState {
    component = <AbilityChooser/>

    getNextState(gameState, frontendState) { // {company: Company.WORDOFMOUTH, agents: 1, type: conglomerate.OMNICORP}
        if(gameState.takeover.companyTiles[0].agents > gameState.takeover.companyTiles[1].agents ||
            (
                gameState.takeover.consultant === consultant.MILITARY_CONTRACTOR &&
                gameState.takeover.companyTiles[0].agents >= gameState.takeover.companyTiles[1].agents
            )
        )

        if (gameState.takeover.ability.choice === false)
            if (gameState.takeover.consultant === consultant.DEALMAKER)
                return frontendState.takeover.DEALMAKER_DRAW_TWO_CARDS_FROM_DECK;
            else
                return frontendState.DONE;
        if (gameState.takeover.ability.choice !== false && gameState.takeover.ability.choice !== null) {
            if (gameState.takeover.consultant === consultant.DEALMAKER)
                return frontendState.takeover.DEALMAKER_DRAW_TWO_CARDS_FROM_DECK;
            switch (gameState.takeover.ability.choice) {
                case CompanyType.BROADCAST_NETWORK: return frontendState.takeover.BROADCAST_NETWORK_PICK_COMPANIES;
                case CompanyType.GUERRILLA_MARKETING: return frontendState.takeover.GUERRILLA_MARKETING_PICK_OPPONENT;
                case CompanyType.PRINT_MEDIA: return frontendState.takeover.PRINT_MEDIA_PICK_YOUR_CONGLOMERATE;
                case CompanyType.AMBIENT_ADVERTISING: return frontendState.takeover.AMBIENT_ADVERTISING_PICK_OPPONENT;
                case CompanyType.SOCIAL_MEDIA: return frontendState.takeover.SOCIAL_MEDIA_PICK_HQ;
                case CompanyType.ONLINE_MARKETING: return frontendState.takeover.ONLINE_MARKETING_PICK_COMPANIES;
            }
        }
    }
}
