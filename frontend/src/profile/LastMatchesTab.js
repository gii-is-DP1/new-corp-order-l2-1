import React, {useEffect, useState} from "react";
import fetchAuthenticated from "../util/fetchAuthenticated";
import {dangerBackground, orange, successBackground} from "../util/Colors";
import ListLine from "../components/ListLine";
import Button, {ButtonType} from "../components/Button";
import {Text} from "../components/Text";
import List from "../components/List";

export function LastMatchesTab({username, navigate, rowListStyle}) {
    const [lastMatches, setLastMatches] = useState(null)

    const fetchLastMatches = async () => {
        try {
            setLastMatches(await fetchAuthenticated(`/api/v1/players/${username}/lastMatches`, "GET")
                .then(async response => await response.json()));
        } catch (error) {
            console.log(error)
        }
    }

    useEffect(() => {
        fetchLastMatches()
    }, []);

    let lastMatchesItems = lastMatches?.map(request => {
        const matchResult = request.result
        const resultColor = matchResult === "WON" ?
            successBackground : matchResult === "LOST" ? dangerBackground : orange
        return (
            <ListLine sideContent={(
                <Button onClick={() => navigate(`/match/${request.matchCode}/stats`)}
                        buttonType={ButtonType.secondaryLight}>
                    View Stats
                </Button>)}>
                <Text>You </Text>
                <Text style={{color: resultColor}}>{matchResult} </Text>
                <Text>Match with code #{request.matchCode}</Text>
            </ListLine>
        )
    })

    return (
        <div>
            <List style={rowListStyle}>
                {lastMatchesItems}
            </List>
        </div>
    )
}
