import React, {useContext, useEffect, useState} from "react";
import {startingState} from "../data/MockupData";
import css from "./game.module.css";
import {State} from "../data/State";
import {HandViewer} from "./viewers/HandViewer";
import {HQViewer} from "./viewers/HQViewer";
import {GeneralSupplyViewer} from "./viewers/GeneralSupplyViewer";
import {OpponentsHqViewer} from "./viewers/OpponentsHqViewer";
import {CompanyMatrixViewer} from "./viewers/CompanyMatrixViewer";
import fetchAuthenticated from "../../util/fetchAuthenticated";
import {useParams} from "react-router-dom";
import {Info} from "../Match";
import ProfilePicture from "../../components/ProfilePicture";
import {black} from "../../util/Colors";

export const StateContext = React.createContext({})

export function Game() {
    const [gameState, setGameState] = useState(startingState);
    if(gameState !== startingState)
        setGameState(startingState);
    const initialContext = new State(gameState, setGameState);
    return <div className={css.game}>
        <StateContext.Provider value={initialContext}>
            <div style={{width:"100%"}}>
                <Background/>
            </div>
            <div style={{position: "absolute",left:0, right:0, top:0, bottom:0, width:"100%", height:"100%"}}>
                <Foreground/>
            </div>
        </StateContext.Provider>
    </div>;
}

function Background() {
    return <>
        <CompanyMatrixViewer/>
    </>
}

function Foreground() {
    const context = useContext(StateContext);
    const info = useContext(Info);
    const FrontendView = () => context.frontendView;

    return <>
        {info.players.map(p =>
            <div style={{width:"80px", height:"80px"}}>
                <ProfilePicture url={p.propic}/>
            </div>
        )}

        <OpponentsHqViewer/>

        <div style={{background: black, position:"fixed", width:"100%", height:"100%", top:0}}>
            <FrontendView/>
        </div>

        <div style={{display:"flex", position:"fixed", bottom:"0", width:"100%", justifyContent:"space-evenly"}}>
            <HandViewer/>
            <HQViewer/>
            <GeneralSupplyViewer/>
        </div>
    </>
}
