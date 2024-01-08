import React, {useState} from "react";
import {startingState} from "../data/MockupData";
import css from "./game.module.css";
import {State} from "../data/State";
import {HandViewer} from "./viewers/HandViewer";
import {HQViewer} from "./viewers/HQViewer";
import {GeneralSupplyViewer} from "./viewers/GeneralSupplyViewer";
import {OpponentsHqViewer} from "./viewers/OpponentsHqViewer";
import {CompanyMatrixViewer} from "./viewers/CompanyMatrixViewer";

export const StateContext = React.createContext({})

export function Game() {
    const [gameState, setGameState] = useState(startingState);
    const initialContext = new State(gameState, setGameState);
    const FrontendView = () => initialContext.frontendView;


    //TODO: view company matrix
    return <div className={css.game}>
        <StateContext.Provider value={initialContext}>
            <FrontendView/>
            <Viewers/>
        </StateContext.Provider>
    </div>;
}

function Viewers(){
    return <>
           <CompanyMatrixViewer/>
            <HandViewer/>
            <HQViewer/>
            <GeneralSupplyViewer/>
            <OpponentsHqViewer/>
    </>
}
