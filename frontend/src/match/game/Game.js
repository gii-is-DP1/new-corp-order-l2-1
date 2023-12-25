import {consultant} from "../data/MatchEnums";
import Conglomerate from "../components/Conglomerate";
import React, {useState} from "react";
import {ViewerContainer} from "./viewer/Viewer";
import {Consultant} from "../components/Consultant";
import { startingState} from "./gameLogic";
import css from "./game.module.css";
import {CurrentGameView} from "./CurrentGameView";

import {State} from "../data/State";

export const StateContext = React.createContext({})

export function Game() {
    const [gameState, setGameState] = useState(startingState);

    return <div className={css.game}>
        <StateContext.Provider value={new State(gameState, setGameState)}>
            <CurrentGameView/>
        </StateContext.Provider>
    </div>;
    /*<div>
              <HandViewer items={hand.components}/>
              <HQViewer hqItems={[...hqConglomerates, ...hqConsultants.components, ...secretObjectives.components]}/>
              <GeneralSupplyViewer items={[...openDisplay.components, <Deck/>, ...generalSupplyConsultants]}/>
              {state.game.opponents.map(viewOpponent)}
          </div>*/
}



function viewOpponent(opponent) {
    const opponentHqConglomerates = opponent.hq.conglomerates.map((item) => <Conglomerate
        conglomerate={item.type} isRotated={item.isRotated}/>);
    const opponentHqConsultants = [
        ...[...Array(opponent.hq.consultants.DEALMAKER)].map(() => <Consultant src={consultant.DEALMAKER}/>),
        ...[...Array(opponent.hq.consultants.CORPORATE_LAWYER)].map(() => <Consultant
            src={consultant.CORPORATE_LAWYER}/>),
        ...[...Array(opponent.hq.consultants.MEDIA_ADVISOR)].map(() => <Consultant src={consultant.MEDIA_ADVISOR}/>),
        ...[...Array(opponent.hq.consultants.MILITARY_CONTRACTOR)].map(() => <Consultant
            src={consultant.MILITARY_CONTRACTOR}/>),
    ];

    return <>
        <ViewerContainer title={opponent.username + "'s HQ"}
                         containerStyle={{display: "flex", flexWrap: "wrap"}}
                         buttonContent={<p>View {opponent.username}'s HQ</p>}
                         items={[...opponentHqConglomerates, ...opponentHqConsultants]}/>
    </>
}

