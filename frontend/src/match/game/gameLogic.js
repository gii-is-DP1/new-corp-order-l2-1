import React from "react";
import {PlotFirstConglomerateState} from "./views/plot/PlotFirstConglomeratePicker";
import {
    PlotSecondConglomerateState
} from "./views/plot/PlotSecondConglomeratePicker";

import {
    InfiltrateConglomeratesState
} from "./views/infiltrate/InfiltrateConglomerateState";
import {InfiltrateConsultantState} from "./views/infiltrate/InfiltrateConsultantState";
import {InfiltrateCompanyState} from "./views/infiltrate/InfiltrateCompanyState";
import {MediaAdvisorConglomerateState} from "./views/infiltrate/MediaAdvisorConglomerateState";
import {CorporateLawyerCompanyState} from "./views/infiltrate/CorporateLawyerCompanyState";
import {CorporateLawyerConglomeratesState} from "./views/infiltrate/CorporateLawyerConglomeratesState";
import {InfiltrateNewConsultantState} from "./views/infiltrate/InfiltrateNewConsultantState";
import {TakeoverConsultantState} from "./views/takeover/TakeoverConsultantState";
import {TakeoverConglomeratesState} from "./views/takeover/TakeoverConglomeratesState";
import {TakeoverCompanyTilesState} from "./views/takeover/TakeoverCompanyTilesState";
import {DealmakerDrawTwoCardsFromDeckState} from "./views/takeover/DealmakerDrawTwoCardsFromDeckState";
import {DoneState} from "./views/DoneState";
import {ChooseActionState} from "./views/ChooseActionState";
import {BroadcastNetworkCompaniesState} from "./views/takeover/BroadcastNetworkCompaniesState";
import {ActivateAbilityState} from "./views/takeover/ActivateAbilityState";
import {GuerrillaMarketingConglomeratesState} from "./views/takeover/GuerrillaMarketingConglomeratesState";
import {GuerrillaMarketingOpponentState} from "./views/takeover/GuerrillaMarketingOpponentState";

const frontendState = {
    plot: {//TODO: some picker should be skippable (selectors have a "canSkip" property)
        DRAW_FIRST_CONGLOMERATE: PlotFirstConglomerateState,
        DRAW_SECOND_CONGLOMERATE: PlotSecondConglomerateState
    },
    infiltrate: {
        PICK_CONGLOMERATES: InfiltrateConglomeratesState,
        PICK_COMPANY: InfiltrateCompanyState,
        PICK_CONSULTANT: InfiltrateConsultantState,
        MEDIA_ADVISOR_PICK_CONGLOMERATE: MediaAdvisorConglomerateState,
        CORPORATE_LAWYER_PICK_CONGLOMERATES: CorporateLawyerConglomeratesState,
        CORPORATE_LAWYER_PICK_COMPANY: CorporateLawyerCompanyState,
        TAKE_CONSULTANT: InfiltrateNewConsultantState,
    },
    takeover: {
        PICK_CONSULTANT: TakeoverConsultantState,
        PICK_CONGLOMERATES: TakeoverConglomeratesState,
        PICK_TWO_COMPANY_TILES: TakeoverCompanyTilesState,
        ACTIVATE_ABILITY: ActivateAbilityState,
        DEALMAKER_DRAW_TWO_CARDS_FROM_DECK: DealmakerDrawTwoCardsFromDeckState,
        BROADCAST_NETWORK_PICK_COMPANIES: BroadcastNetworkCompaniesState,
        GUERRILLA_MARKETING_PICK_OPPONENT: GuerrillaMarketingOpponentState,
        GUERRILLA_MARKETING_PICK_CONGLOMERATES: GuerrillaMarketingConglomeratesState},
        /*PRINT_MEDIA_PICK_YOUR_CONGLOMERATE: PrintMediaFirstConglomerateState,
        PRINT_MEDIA_PICK_OTHER_CONGLOMERATE: PrintMediaSecondConglomerateState,
        AMBIENT_ADVERTISING_PICK_CONGLOMERATES: AmbientAdvertisingConglomeratesState,
        SOCIAL_MEDIA_PICK_CONGLOMERATE: SocialMediaConglomerateState,
        ONLINE_MARKETING_PICK_COMPANIES: OnlineMarketingCompaniesState,
    },
    TODO: implement company abilities states and Discard state

    DISCARD:DiscardState,
     */
    DONE: DoneState,
    CHOOSE_ACTION: ChooseActionState,
}

export function getCurrentFrontendView(state) {
    let currentState = frontendState.CHOOSE_ACTION;


   // return new frontendState.infiltrate.CORPORATE_LAWYER_PICK_COMPANY(state, frontendState).component;
    while (true) {
        const instance = new currentState(state, frontendState);
        const nextState = instance.nextState;
        if (nextState == null)
            return instance.component;
        currentState = nextState;
    }
}

/* TODO: implement company abilities logic as FSM and THEN delete this horrendous comment

    } else if (state.action === TAKEOVER) {

        if (state.takeover.conglomerates === null)
            return frontendState.takeover.PICK_CONGLOMERATES;
        if (state.takeover.companyTiles === null)
            return frontendState.takeover.PICK_TWO_COMPANY_TILES;
        if (state.takeover.consultant === consultant.DEALMAKER)
            return frontendState.takeover.DEALMAKER_DRAW_TWO_CARDS_FROM_DECK;
        if (state.takeover.ability.choice === null && state.takeover.canActivateCompanyAbility)
            return frontendState.takeover.ACTIVATE_ABILITY;
        if (state.takeover.ability.choice === CompanyType.BROADCAST_NETWORK && state.takeover.ability.broadcastNetwork.companies === null)
            return frontendState.BROADCAST_NETWORK_PICK_COMPANIES;
        if (state.takeover.ability.choice === CompanyType.AMBIENT_ADVERTISING && state.takeover.ability.ambientAdvertising.conglomerates === null)
            return frontendState.AMBIENT_ADVERTISING_PICK_CONGLOMERATES;
        if (state.takeover.ability.choice === CompanyType.SOCIAL_MEDIA && state.takeover.ability.socialMedia.conglomerate === null)
            return frontendState.SOCIAL_MEDIA_PICK_CONGLOMERATE;
        if (state.takeover.ability.choice === CompanyType.PRINT_MEDIA && state.takeover.ability.printMedia.yourConglomerate === null)
            return frontendState.PRINT_MEDIA_PICK_YOUR_CONGLOMERATE;
        if (state.takeover.ability.choice === CompanyType.PRINT_MEDIA && state.takeover.ability.printMedia.otherConglomerate === null)
            return frontendState.PRINT_MEDIA_PICK_OTHER_CONGLOMERATE;
        if (state.takeover.ability.choice === CompanyType.GUERRILLA_MARKETING && state.takeover.ability.guerrillaMarketing.conglomerates === null)
            return frontendState.GUERRILLA_MARKETING_PICK_CONGLOMERATES;
        if (state.takeover.ability.choice === CompanyType.ONLINE_MARKETING && state.takeover.ability.onlineMarketing.companies === null)
            return frontendState.ONLINE_MARKETING_PICK_COMPANIES;
    }
    if (hand.values.length > 6)
        return frontendState.DISCARD;
    return frontendState.DONE;
}
*/
