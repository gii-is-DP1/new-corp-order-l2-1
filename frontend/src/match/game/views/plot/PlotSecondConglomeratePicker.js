import {DrawConglomerate} from "./DrawConglomerate";
import {FrontendState} from "../FrontendState";
import {PlotFirstConglomeratePicker} from "./PlotFirstConglomeratePicker";

function PlotSecondConglomeratePicker() {
    return DrawConglomerate(false);
}

export class PlotSecondConglomerateState extends FrontendState {
    component = <PlotSecondConglomeratePicker/>
    getNextState(gameState, frontendState){
        if(gameState.plot.secondConglomerate !== null)
            return frontendState.DONE;
    }
}
