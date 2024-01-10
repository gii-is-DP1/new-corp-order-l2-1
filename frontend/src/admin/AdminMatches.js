import AppNavbar from "../AppNavbar";
import {black, dangerBackground, successBackground, white} from "../util/Colors";
import {Title} from "../components/Title";
import List from "../components/List";
import ListLine from "../components/ListLine";
import Button, {ButtonType} from "../components/Button";
import {Subtitle} from "../components/Subtitle";
import {useEffect, useState} from "react";
import fetchAuthenticated from "../util/fetchAuthenticated";
import {useNavigate} from "react-router-dom";
import {Text} from "../components/Text";
import {Pressable} from "../components/Pressable";

export function AdminMatches() {
    const [matchesData, setMatchesData] = useState([])
    const [page, setPage] = useState(0)
    const navigate = useNavigate()

    const fetchMatches = async () => {
        try {
            setMatchesData([
                ...matchesData,
                ...await fetchAuthenticated(`/api/v1/matches?page=${page}`, "GET")
                    .then(async response => await response.json())
            ])
        } catch (error) {
            console.log(error)
        }
    }

    useEffect(() => {
        fetchMatches()
    }, [page]);

    if (!matchesData) {
        return <></>
    }

    console.log(matchesData)
    const content = {
        display: "flex",
        flexDirection: "column",
        alignItems: "center"
    }

    let matchItems = matchesData?.map(request => {
        const stateColor = request.state === "FINISHED" ? dangerBackground : successBackground
        return (
            <ListLine sideContent={(<>
                    {(request.state === "WAITING" || request.state === "PLAYING") &&
                        <Button style={() => navigate(`/match/${request.code}`)} buttonType={ButtonType.primary}>
                            Spectate
                        </Button>}
                    {request.state === "FINISHED" &&
                        <Button onClick={() => navigate(`/match/${request.code}/stats`)}
                                buttonType={ButtonType.secondaryLight}>
                            View stats
                        </Button>}
                </>
            )}>
                <Subtitle>· Match #{request.code} |</Subtitle>
                <Subtitle style={{color: stateColor}}>{request.state}</Subtitle>
                <Subtitle>| {request.players}/{request.maxPlayers} players</Subtitle>
            </ListLine>
        )
    })

    return (
        <div style={{height: "100%", backgroundColor: black}}>
            <AppNavbar/>
            <section style={content}>
                <Title style={{fontSize: "60px", color: white}}>
                    Matches
                </Title>
                <Subtitle style={{fontSize: "15px", color: white}}>
                    Select a match to join (if currently playing) or view stats
                </Subtitle>
                <div>
                    <List style={{
                        marginTop: "15px",
                        maxHeight: "600px",
                        width: "800px",
                        backgroundColor: black,
                        overflow: "auto"
                    }}>
                        {matchItems}
                    </List>
                </div>
                <Pressable onClick={() => setPage(page + 1)}>
                    <Text style={{color: white}}>View more</Text>
                </Pressable>
            </section>
        </div>
    )
}
