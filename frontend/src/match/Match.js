import {defaultMatchInfo, startingState} from "./data/MockupData";
import React, {useEffect, useState} from "react"
import css from "./match.module.css";
import {Main} from "./Main";
import {RightBar} from "./RightBar";
import {useParams} from "react-router-dom";
import fetchAuthenticated from "../util/fetchAuthenticated";
import tokenService from "../services/token.service";
import {Company, conglomerate, propics} from "./data/MatchEnums";

export const Info = React.createContext({...defaultMatchInfo})
let matchInfo = {...defaultMatchInfo};

export default function Match() {
    const [matchData, setMatchData] = useState(null);
    const {id} = useParams();
    matchInfo = {...defaultMatchInfo};

    const [propic, setPropic] = useState(null);
    const fetchPropic = async () => {
        try {
            setPropic(await fetchAuthenticated(`/api/v1/users/${tokenService.getUser().username}/picture`, "GET")
                .then(async response => await response.json()));
        } catch (error) {
            console.log(error.message)
        }
    };

    const fetchMatchData = async () => {
        try {
            setMatchData(await fetchAuthenticated(`/api/v1/matches/${id}`, "GET")
                .then(async response => await response.json()));
            return matchData;
        } catch (error) {
            console.log(error.message)
        }
    };

    useEffect(() => {
        fetchMatchData().then(r => console.log(r))
        fetchPropic()
    }, []);


    useEffect(
        () => {
            if (matchData != null && matchData.turn !== tokenService.getUser().id)
                console.log("a");
               // setInterval(() => fetchMatchData(), 5000);
        }
    )

    const isLoading = matchData == null || propic == null;
    if (isLoading) {
        return <LoadingScreen/>;
    } else {
        setContext(id, matchData, propic);
        return <LoadedPage key={id}/>
    }
}

function LoadingScreen() {
    return (
        <div className={css.match}>
            <p>loading...</p>
        </div>
    )
}

function LoadedPage() {
    return (
        <div className={css.match}>
            <Info.Provider value={matchInfo}>
                <Main/>
            </Info.Provider>
        </div>);
}

function setContext(id, matchData, propic) {
    if (matchInfo.code !== id)
        matchInfo = {...defaultMatchInfo};

    matchInfo.code = id;
    matchInfo.inLobby = matchData.state === "WAITING";
    matchInfo.maxPlayers = matchData.maxPlayers;
    matchInfo.isAdmin = matchData.host === tokenService.getUser().id;
    if (!matchData.isSpectating && matchData.spectating)
        matchInfo.hasBeenKicked = true;
    matchInfo.wasSpectating = matchInfo.isSpectating;
    matchInfo.isSpectating = matchData.spectating;
    if (!matchData.spectating)
        matchInfo.players = [{propic: propics[propic.picture], username: tokenService.getUser().username}];
    else matchInfo.players = [];
    matchInfo.players = matchInfo.players.concat(matchData.opponents.map(opponent => {
        return {propic: propics[opponent.picture], username: opponent.username}
    }));

    if (matchData.state === "PLAYING") {
        startingState.game.companies = matchData.companyMatrix.map(c => {
            return {company: Company[c.company], agents: c.agents, type: conglomerate[c.currentConglomerate]}
        });
        startingState.turn = matchData.turn.player;
    }
}
