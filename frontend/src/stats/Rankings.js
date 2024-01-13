import React, {useEffect, useState} from "react";
import fetchAuthenticated from "../util/fetchAuthenticated";
import {useNavigate, useParams} from "react-router-dom";
import AppNavbar from "../AppNavbar";
import {PressableText} from "../components/PressableText";
import {black, white} from "../util/Colors";
import {Text} from "../components/Text";
import ProfilePicture from "../components/ProfilePicture";
import List from "../components/List";
import ListLine from "../components/ListLine";
import {Title} from "../components/Title";
import {Subtitle} from "../components/Subtitle";
import {Pressable} from "../components/Pressable";

export function Rankings() {
    const {stat} = useParams()
    const [rankingData, setRankingData] = useState(null)
    const [page, setPage] = useState(0)
    const navigate = useNavigate()

    if (!stat) {
        navigate("/rankings/games_won")
    }

    const fetchRankingData = async () => {
        try {
            if (!stat) return
            setRankingData(await fetchAuthenticated(`/api/v1/players/ranking?stat=${stat.toUpperCase()}&page=${page}`,
                "GET")
                .then(async response => await response.json()))
        } catch (error) {
            console.log(error)
        }
    }

    useEffect(() => {
        fetchRankingData()
    }, [stat, page]);

    if (!rankingData) {
        return <></>
    }

    let items = rankingData?.map(request => {
        return (
            <ListLine>
                <ProfilePicture url={request.user.picture} style={{height: "30px", width: "30px"}}/>
                <Text>{request.user.username}: {request.amount}</Text>
            </ListLine>
        )
    })

    return (
        <div style={{display: "flex", flexDirection: "column", height: "100%", backgroundColor: black}}>
            <AppNavbar/>
            <section style={{display: "flex", flexDirection: "column", justifyContent: "center", alignItems: "center"}}>
                <Title style={{fontSize: "60px", color: white}}>
                    Ranking
                </Title>
                <Subtitle style={{fontSize: "15px", color: white}}>
                    See who are the top players
                </Subtitle>
            </section>
            <section style={{display: "flex", justifyContent: "center", marginTop: "25px", gap: "25px"}}>
                <PressableText style={{color: white}}
                               underlined={stat === "games_won"}
                               onClick={() => navigate("/rankings/games_won")}>
                    Wins
                </PressableText>
                <PressableText style={{color: white}}
                               underlined={stat === "times_played"}
                               onClick={() => navigate("/rankings/times_played")}>
                    Times played
                </PressableText>
            </section>
            <section style={{display: "flex", justifyContent: "center", color: white}}>
                <List style={{
                    backgroundColor: black,
                    maxWidth: "300px",
                    maxHeight: "525px",
                    marginTop: "15px",
                    overflow: "auto"
                }}>
                    {items}
                </List>
            </section>
            <Pressable onClick={() => setPage(page + 1)}>
                <Text style={{color: white}}>View more</Text>
            </Pressable>
        </div>
    )
}
