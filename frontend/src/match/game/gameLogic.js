import {CompanyType, consultant, frontendState, INFILTRATE, PLOT, TAKEOVER} from "../data/MatchEnums";
import {mockUpData} from "../data/MockupData";
import {ConglomerateMultiset} from "../components/multisets/ConglomerateMultiset";

export function getFrontendState(state) {
    const hand = new ConglomerateMultiset(state.game.player.hand);

    if (state.action === null)
        return frontendState.CHOOSE_ACTION;
    if (state.action === PLOT) {
        if (state.plot.firstConglomerate === null)
            return frontendState.plot.DRAW_FIRST_CONGLOMERATE;
        if (state.plot.secondConglomerate === null)
            return frontendState.plot.DRAW_SECOND_CONGLOMERATE;
    } else if (state.action === INFILTRATE) {
        if (state.infiltrate.conglomerates === null)
            return frontendState.infiltrate.PICK_CONGLOMERATES;
        if (state.infiltrate.companyTile === null)
            return frontendState.infiltrate.PICK_COMPANY;
        if (state.infiltrate.consultant === null)
            return frontendState.infiltrate.PICK_CONSULTANT
        if (state.infiltrate.consultant === consultant.MEDIA_ADVISOR)
            return frontendState.infiltrate.MEDIA_ADVISOR_PICK_CONGLOMERATE;
        if (state.infiltrate.consultant === consultant.CORPORATE_LAWYER) {
            if (state.infiltrate.corporateLawyer.conglomerates === null)
                return frontendState.infiltrate.CORPORATE_LAWYER_PICK_CONGLOMERATES;
            if (state.infiltrate.corporateLawyer.company === null)
                return frontendState.infiltrate.CORPORATE_LAWYER_PICK_COMPANY;
        }
        if (state.infiltrate.consultant >= 3 && state.infiltrate.takenConsultant === null)
            return frontendState.infiltrate.TAKE_CONSULTANT;
    } else if (state.action === TAKEOVER) {
        if (state.takeover.consultant === null && state.takeover.consultant !== -1)
            return frontendState.takeover.PICK_CONSULTANT;
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

export const startingState = {
    game: mockUpData,
    action: null,
    plot: {
        firstConglomerate: null,
        secondConglomerate: null,
    },
    infiltrate: {
        conglomerates: null,
        companyTile: null,
        consultant: null,
        mediaAdvisor: {
            conglomerate: null,
        },
        corporateLawyer: {
            conglomerates: null,
            company: null,
        },
        takenConsultant: null
    },
    takeover: {
        consultant: null,
        conglomerates: null,
        companyTiles: null,
        canActivateCompanyAbility: false,
        ability: {
            choice: null,
            broadcastNetwork: {
                companies: null,
            },
            guerrillaMarketing: {
                conglomerates: null,
            },
            printMedia: {
                yourConglomerate: null,
                otherHq: null,
                otherConglomerate: null,
            },
            ambientAdvertising: {
                conglomerates: null,
            },
            socialMedia: {
                hq: null,
                conglomerate: null,
            },
            onlineMarketing: {
                companies: null,
            }
        },
        dealmaker: {
            conglomerates: null,
        }
    }
};
