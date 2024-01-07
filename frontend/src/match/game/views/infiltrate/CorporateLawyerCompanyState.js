import {FrontendState} from "../FrontendState";
import {PlotFirstConglomeratePicker} from "../plot/PlotFirstConglomeratePicker";
import {useContext} from "react";
import {StateContext} from "../../Game";
import {pickCompany, pickManyConglomeratesOfTheSameColor, pickOneCard} from "../../selector/pickers/Pickers";

function CorporateLawyerCompanyPicker() {
    const context = useContext(StateContext);

    return pickCompany(context.companyTiles.components, (selected) => {
        context.state.infiltrate.corporateLawyer.company = context.state.game.companies[selected[0]];
        context.update();
    }, 1)
}

export class CorporateLawyerCompanyState extends FrontendState {
    component = <CorporateLawyerCompanyPicker/>

    getNextState(gameState, frontendState) {
        if (gameState.infiltrate.corporateLawyer.company !== null)
            return frontendState.DONE;
    }
}
