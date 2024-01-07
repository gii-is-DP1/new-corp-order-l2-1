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
import {PrintMediaFirstConglomerateState} from "./views/takeover/PrintMediaFirstConglomerateState";
import {PrintMediaOtherHqState} from "./views/takeover/PrintMediaOtherHqState";
import {PrintMediaSecondConglomerateState} from "./views/takeover/PrintMediaSecondConglomerateState";
import {AmbientAdvertisingOpponentState} from "./views/takeover/AmbientAdvertisingOpponentState";
import {AmbientAdvertisingConglomeratesState} from "./views/takeover/AmbientAdvertisingConglomeratesState";
import {OnlineMarketingCompaniesState} from "./views/takeover/OnlineMarketingCompaniesState";
import {SocialMediaHqState} from "./views/takeover/SocialMediaHqState";
import {SocialMediaConglomerateState} from "./views/takeover/SocialMediaConglomerateState";

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
        GUERRILLA_MARKETING_PICK_CONGLOMERATES: GuerrillaMarketingConglomeratesState,
        PRINT_MEDIA_PICK_YOUR_CONGLOMERATE: PrintMediaFirstConglomerateState,
        PRINT_MEDIA_PICK_OTHER_HQ: PrintMediaOtherHqState,
        PRINT_MEDIA_PICK_OTHER_CONGLOMERATE: PrintMediaSecondConglomerateState,
        AMBIENT_ADVERTISING_PICK_OPPONENT: AmbientAdvertisingOpponentState,
        AMBIENT_ADVERTISING_PICK_CONGLOMERATES: AmbientAdvertisingConglomeratesState,
        SOCIAL_MEDIA_PICK_HQ: SocialMediaHqState,
        SOCIAL_MEDIA_PICK_CONGLOMERATE: SocialMediaConglomerateState,
        ONLINE_MARKETING_PICK_COMPANIES: OnlineMarketingCompaniesState,
    },
    /*TODO: implement company abilities states and Discard state

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

/*
    if (hand.values.length > 6)
        return frontendState.DISCARD;
    return frontendState.DONE;
}
*/
