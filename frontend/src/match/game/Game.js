import React, {useEffect, useState} from "react";
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

export const StateContext = React.createContext({})

export function Game() {
    const [gameState, setGameState] = useState(startingState);
    const initialContext = new State(gameState, setGameState);
    const [matchData, setMatchData] = useState(null);
    const {id} = useParams();
    const FrontendView = () => initialContext.frontendView;
    const fetchGameData = async () => {
        try {
            setMatchData(await fetchAuthenticated(`/api/v1/matches/${id}`, "GET")
                .then(async response => await response.json()));
        } catch (error) {
            console.log(error.message)
        }
    };

    useEffect(() => {
        fetchGameData()
        setInterval(() => fetchGameData(), 2500)
    }, []);

    //console.log(matchData)
    if(matchData != null){
        console.log("XOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXOXO");
        console.log(matchData.companyMatrix);
        const companies = [];
        for (let i = 0; i < matchData.companyMatrix.length; i++) {
        const company = matchData.companyMatrix[i];
        companies.push({company: company.company, agents: company.agents, type: company.currentConglomerate});
        initialContext.state.game.companies = companies;
        console.log(initialContext.state.game.companies);
    }}



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
