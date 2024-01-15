import AppNavbar from "../AppNavbar";
import {black, white} from "../util/Colors";
import {useEffect, useState} from "react";
import fetchAuthenticated from "../util/fetchAuthenticated";
import {useParams} from "react-router-dom";
import {Title} from "../components/Title";
import {Subtitle} from "../components/Subtitle";
import {Text} from "../components/Text";
import ProfilePicture from "../components/ProfilePicture";
import {propics} from "../match/data/MatchEnums";

export function MatchStats() {
    const [matchStats, setMatchStats] = useState(null)
    const {matchCode} = useParams()

    const fetchMatchStats = async () => {
        try {
            setMatchStats(await fetchAuthenticated(`/api/v1/matches/${matchCode}/stats`, "GET")
                .then(async response => await response.json()))
        } catch (error) {
            console.log(error)
        }
    }
    useEffect(() => {
        fetchMatchStats()
    }, []);

    if (!matchStats) {
        return <></>
    }

    const content = {
        flex: 1,
        display: "flex",
        flexDirection: "column",
        overflow: "auto"
    }

    const headerStyle = {
        marginLeft: "auto",
        marginRight: "auto",
        marginTop: "25px",
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        gap: "10px"
    }

    const bodyStyle = {
        display: "flex",
        flexDirection: "column",
        marginTop: "25px",
        alignItems: "center"
    }

    const statsStyle = {
        display: "flex",
        justifyContent: "center",
        gap: "5px",
        marginBottom: "5px",
        marginTop: "20px"
    }

    let winnersItems = matchStats.winners?.map(request => {
        return <Text style={{color: white}}> 👑 {request}</Text>
    })

    const abilityItems = (request) => {
        return (
            Object.entries(request.abilityUses)
                .map(([ability, count]) => (
                    <Text style={{display: "flex", marginLeft: "15px", color: white}}>• {ability}: {count}</Text>
                )))
    }

    let agentItems = (request) => {
        return (
            Object.entries(request.agentUses)
                .map(([agent, count]) => (
                    <Text style={{display: "flex", marginLeft: "15px", color: white}}>• {agent}: {count}</Text>
                )))
    }

    let consultantItems = (request) => {
        return (
            Object.entries(request.consultantUses)
                .map(([consultant, count]) => (
                    <Text style={{display: "flex", marginLeft: "15px", color: white}}>• {consultant}: {count}</Text>
                )))
    }

    let shareItems = (request) => {
        return (
            Object.entries(request.shareUses)
                .map(([share, count]) => (
                    <Text style={{display: "flex", marginLeft: "15px", color: white}}>• {share}: {count}</Text>
                )))
    }

    let playersItems = matchStats.players?.map(request => {
        return (
            <div style={{display: "flex", flexDirection: "column"}}>
                <div style={statsStyle}>
                    <ProfilePicture url={propics[request.user.picture]} style={{width: "25px", height: "25px"}}/>
                    <Text style={{color: white}}>{request.user.username}</Text>
                </div>
                <div style={{display: "flex", flexDirection: "column"}}>
                    <Text style={{marginLeft: "15px", color: white}}>
                        - Victory points: {request.victoryPoints}
                    </Text>
                    <Text style={{marginLeft: "15px", color: white}}>
                        - Times Plotted: {request.timesPlotted}
                    </Text>
                    <Text style={{marginLeft: "15px", color: white}}>
                        - Times Infiltrated: {request.timesInfiltrated}
                    </Text>
                    <Text style={{marginLeft: "15px", color: white}}>
                        - Times Taken Over: {request.timesTakenOver}
                    </Text>
                    <div style={{display: "flex", flexDirection: "column"}}>
                        <Text style={{marginLeft: "15px", color: white}}>
                            - Ability uses: {abilityItems(request)}
                        </Text>
                        <Text style={{marginLeft: "15px", color: white}}>
                            - Agent uses: {agentItems(request)}
                        </Text>
                        <Text style={{marginLeft: "15px", color: white}}>
                            - Consultant uses: {consultantItems(request)}
                        </Text>
                        <Text style={{marginLeft: "15px", color: white}}>
                            - Share uses: {shareItems(request)}
                        </Text>
                    </div>
                </div>
            </div>
        )
    })

    return (
        <div style={{
            display: "flex",
            flexDirection: "column",
            height: "100%",
            backgroundColor: black
        }}>
            <AppNavbar/>
            <div style={content}>
                <section style={headerStyle}>
                    <Title style={{fontSize: "36px", color: white}}>
                        Match #{matchStats.code}
                    </Title>
                    <Subtitle style={{fontSize: "18px", color: white}}>
                        Played by {matchStats.maxPlayers} players in {matchStats.mode} mode
                    </Subtitle>
                    <Subtitle style={{fontSize: "18px", color: white}}>
                        The game
                        lasted {Date.parse(matchStats.endTime) / 60000 - Date.parse(matchStats.startTime) / 60000} minutes
                    </Subtitle>
                </section>
                <section style={bodyStyle}>
                    {matchStats.winners.length > 1 &&
                        <Title style={{color: white, marginBottom: "5px"}}>Winners</Title>}
                    {matchStats.winners.length === 1 &&
                        <Title style={{color: white, marginBottom: "5px"}}>Winner</Title>}
                    {winnersItems}
                    <Title style={{color: white, marginTop: "25px"}}> Players</Title>
                    <div style={{display: "flex", gap: "75px"}}> {playersItems} </div>
                </section>
            </div>
        </div>
    )
}
