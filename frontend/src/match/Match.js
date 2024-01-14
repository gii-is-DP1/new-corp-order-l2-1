import {defaultMatchInfo, defaultState, mockUpData} from "./data/MockupData";
import React, {useEffect, useState} from "react"
import css from "./match.module.css";
import {Main} from "./Main";
import {useParams} from "react-router-dom";
import fetchAuthenticated from "../util/fetchAuthenticated";
import tokenService from "../services/token.service";
import {Company, conglomerate, propics, secretObjective} from "./data/MatchEnums";

export const Info = React.createContext({...defaultMatchInfo})
let matchInfo = {...defaultMatchInfo};
export let startingState = {...defaultState};

export default function Match() {
    let [matchData, setMatchData] = useState(null);
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
            const wasEmpty = matchData === null;
            const wasWaiting = wasEmpty ? null : matchData.state === "WAITING"
            const wasPlaying = wasEmpty ? false : matchData.playing;

            matchData = (await fetchAuthenticated(`/api/v1/matches/${id}`, "GET")
                .then(async response => await response.json()));



            const isPLaying = matchData.state === "PLAYING";
            if ( !(matchData.playing) || wasEmpty || !isPLaying || (wasWaiting && isPLaying) || (!wasPlaying && matchData.playing)) {
                setMatchData(matchData)
            }
        } catch (error) {
            console.log(error.message)
        }
    };

    useEffect(() => {
        const fetchData = async () => {
            await fetchPropic();
            await fetchMatchData();
        };
        fetchData();
        const interval = setInterval(() => {
            fetchMatchData();
        }, 5000);

        return () => clearInterval(interval);
    }, []);

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
    {
        matchInfo = {...defaultMatchInfo};
    }
    startingState = {
        game: mockUpData,
        turn: 0,
        discardedConglomerates: null,
        isPlaying: false,
        action: null,
        plot: {
            firstConglomerate: null,
            secondConglomerate: null,
        },
        infiltrate: {
            conglomerate: null,
            conglomerateQuantity: null,
            companyTile: null,
            consultant: null,
            mediaAdvisor: {
                conglomerate: null,
            },
            corporateLawyer: {
                conglomerates: null,
                company: null,
            },
            takenConsultant: null
        },
        takeover: {
            consultant: null,
            conglomerates: null,
            companyTiles: null,
            canActivateCompanyAbility: false,
            ability: {
                choice: null,
                broadcastNetwork: {
                    companies: null,
                },
                guerrillaMarketing: {
                    opponent: null,
                    conglomerates: null,
                },
                printMedia: {
                    yourConglomerate: null,
                    yourIsRotated: null,
                    otherHq: null,
                    otherConglomerate: null,
                    otherIsRotated: null,
                },
                ambientAdvertising: {
                    opponent: null,
                    conglomerates: null,
                },
                socialMedia: {
                    hq: null,
                    conglomerate: null,
                },
                onlineMarketing: {
                    companies: null,
                }
            },
            dealmaker: {
                conglomerates: null,
            }
        }
    };

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
        startingState.isPlaying = matchData.playing;
        startingState.game.player.hand = matchData.player.hand;
        if(startingState.game.player.hand[conglomerate.OMNICORP] === undefined)
            startingState.game.player.hand[conglomerate.OMNICORP] = 0;
        if(startingState.game.player.hand[conglomerate.MEGAMEDIA] === undefined)
            startingState.game.player.hand[conglomerate.MEGAMEDIA] = 0;
        if(startingState.game.player.hand[conglomerate.TOTAL_ENTERTAINMENT] === undefined)
            startingState.game.player.hand[conglomerate.TOTAL_ENTERTAINMENT] = 0;
        if(startingState.game.player.hand[conglomerate.GENERIC_INC] === undefined)
            startingState.game.player.hand[conglomerate.GENERIC_INC] = 0;

        startingState.game.player.hq.secretObjectives = matchData.player.secretObjectives.map(s => secretObjective[s]);
        startingState.game.player.hq.consultants = matchData.player.headquarter.consultants;
        startingState.game.player.hq.rotatedConglomerates = matchData.player.headquarter.usedConglomerateShares ?? []; //TODO: check if are multiset or array
        startingState.game.player.hq.nonRotatedConglomerates = matchData.player.headquarter.conglomerateShares ?? [];
        startingState.game.companies = matchData.companyMatrix.map(c => {
            return {company: Company[c.company], agents: c.agents, type: conglomerate[c.currentConglomerate]}
        });
        startingState.turn = matchData.turn.player;
        startingState.game.generalSupply.consultants = matchData.generalSupply.consultants;
        startingState.game.generalSupply.conglomeratesLeftInDeck = matchData.generalSupply.deckSize;
        startingState.game.generalSupply.openDisplay = matchData.generalSupply.openDisplay;


    }
}
