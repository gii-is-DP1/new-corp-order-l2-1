import {DrawConglomerate} from "./DrawConglomerate";
import {FrontendState} from "../FrontendState";

export function PlotFirstConglomeratePicker() {
    return DrawConglomerate(true);
}

export class PlotFirstConglomerateState extends FrontendState{
    component = <PlotFirstConglomeratePicker/>;
    getNextState(gameState, frontendState){
        if(gameState.plot.firstConglomerate !== null)
            return frontendState.plot.DRAW_SECOND_CONGLOMERATE;
    }
}
