import {Title} from "../components/Title";
import AppNavbar from "../AppNavbar";
import {useEffect, useState} from "react";
import fetchAuthenticated from "../util/fetchAuthenticated";
import {black, white} from "../util/Colors";
import {Subtitle} from "../components/Subtitle";
import {Text} from "../components/Text";

export function AdminMetrics() {
    const [matchMetrics, setMatchMetrics] = useState(null)

    const fetchMatchStats = async () => {
        try {
            setMatchMetrics(await fetchAuthenticated(`/api/v1/metrics`, "GET")
                .then(async response => await response.json()))
        } catch (error) {
            console.log(error)
        }
    }

    useEffect(() => {
        fetchMatchStats()
    }, []);

    if (!matchMetrics) {
        return <></>
    }

    const content = {
        display: "flex",
        flexDirection: "column",
        alignItems: "center"
    }

    console.log(matchMetrics)
    return (
        <div style={{
            display: "flex",
            flexDirection: "column",
            height: "100%",
            backgroundColor: black
        }}>
            <AppNavbar/>
            <section style={content}>
                <Title style={{fontSize: "60px", color: white}}>
                    Metrics
                </Title>
                <div style={{display: "flex", flexDirection: "column"}}>

                    <Subtitle style={{
                        fontSize: "35px",
                        color: white,
                        alignSelf: "center",
                        marginTop: "25px",
                        marginBottom: "10px"
                    }}>
                        Matches duration
                    </Subtitle>
                    <Text style={{fontSize: "20px", color: white}}>
                        - Matches lasted an average of {matchMetrics.averageDuration / 60} minutes.
                    </Text>
                    <Text style={{fontSize: "20px", color: white}}>
                        - Shortest match lasted {matchMetrics.minDuration / 60} minutes.
                    </Text>
                    <Text style={{fontSize: "20px", color: white}}>
                        - Longest match lasted {matchMetrics.maxDuration / 60} minutes.
                    </Text>

                    <Subtitle style={{
                        fontSize: "35px",
                        color: white,
                        alignSelf: "center",
                        marginTop: "25px",
                        marginBottom: "10px"
                    }}>
                        Matches per player
                    </Subtitle>
                    <Text style={{fontSize: "20px", color: white}}>
                        - Players has an average of {matchMetrics.averagePlayedMatches} matches played.
                    </Text>
                    <Text style={{fontSize: "20px", color: white}}>
                        - Player with the less matches played has {matchMetrics.minPlayedMatches} matches played.
                    </Text>
                    <Text style={{fontSize: "20px", color: white}}>
                        - Player with the most matches played has {matchMetrics.maxPlayedMatches} matches played.
                    </Text>

                    <Subtitle style={{
                        fontSize: "35px",
                        color: white,
                        alignSelf: "center",
                        marginTop: "25px",
                        marginBottom: "10px"
                    }}>
                        Actions
                    </Subtitle>
                    <Text style={{fontSize: "20px", color: white}}>
                        - Plot action has been done {matchMetrics.timesPlotted} times.
                    </Text>
                    <Text style={{fontSize: "20px", color: white}}>
                        - Infiltrate action has been done {matchMetrics.timesInfiltrated} times.
                    </Text>
                    <Text style={{fontSize: "20px", color: white}}>
                        - Take Over action has been done {matchMetrics.timesTakenOver} times.
                    </Text>

                    <Subtitle style={{
                        fontSize: "35px",
                        color: white,
                        alignSelf: "center",
                        marginTop: "25px",
                        marginBottom: "10px"
                    }}>
                        Average players per match: {matchMetrics.averageMaxPlayers.toFixed(2)}
                    </Subtitle>
                </div>
            </section>
        </div>
    )
}
