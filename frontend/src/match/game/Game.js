import React, {useState} from "react";
import {startingState} from "../data/MockupData";
import css from "./game.module.css";
import {State} from "../data/State";
import {HandViewer} from "./viewer/HandViewer";
import {HQViewer} from "./viewer/HQViewer";
import {GeneralSupplyViewer} from "./viewer/GeneralSupplyViewer";
import {OpponentsHqViewer} from "./viewer/OpponentsHqViewer";

export const StateContext = React.createContext({})

export function Game() {
    const [gameState, setGameState] = useState(startingState);
    const initialContext = new State(gameState, setGameState);
    const FrontendView = () => initialContext.frontendView;

    //TODO: view company matrix
    return <div className={css.game}>
        <StateContext.Provider value={initialContext}>
            <FrontendView/>
            <div>
                <HandViewer/>
                <HQViewer/>
                <GeneralSupplyViewer/>
                <OpponentsHqViewer/>
            </div>
        </StateContext.Provider>
    </div>;
}
