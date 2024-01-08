import {matchInfo} from "./data/MockupData";
import React, {useEffect, useState} from "react"
import css from "./match.module.css";
import {Main} from "./Main";
import {RightBar} from "./RightBar";
import {useParams} from "react-router-dom";
import fetchAuthenticated from "../util/fetchAuthenticated";
import tokenService from "../services/token.service";

export const Info = React.createContext(matchInfo)

export default function Match() {
    const [matchData, setMatchData] = useState(null);
    const {id} = useParams();

    const fetchMatchData = async () => {
        try {
            setMatchData(await fetchAuthenticated(`/api/v1/matches/${id}`, "GET")
                .then(async response => await response.json()));
        } catch (error) {
            console.log(error.message)
        }
    };

    useEffect(() => {
        fetchMatchData()
        setInterval(() => fetchMatchData(), 5000)
    }, []);

    const isLoading = matchData == null;
    console.log(isLoading);
    if(isLoading) {
        return <LoadingScreen/>;
    }
    else
    {
        setContext(id, matchData);
        return <LoadedPage/>
    }

}

function LoadingScreen(){
    return (
        <div className={css.match}>
            <p>loading...</p>
        </div>
    )
}

function LoadedPage(){
    return (
        <div className={css.match}>
            <Info.Provider value = {matchInfo} >
                <Main/>

            </Info.Provider>
        </div>)  /* <RightBar/>*/
}

function setContext(id, matchData){
    matchInfo.code = id;
    matchInfo.inLobby = matchData.state === "WAITING";
    matchInfo.maxPlayers = matchData.maxPlayers;
    matchInfo.isAdmin = matchData.host === tokenService.getUser().id;
    if(!matchData.spectating)
        matchInfo.players = [{propic: null, username: tokenService.getUser().username}];
    else matchInfo.players = [];
    matchInfo.players = matchInfo.players.concat(matchData.opponents.map(opponent => {return {propic:null,username:opponent.username}}));
}
