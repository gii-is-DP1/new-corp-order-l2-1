import React, {useContext} from "react";
import {Info} from "../Match";
import Button, {ButtonType} from "../../components/Button";
import {Link, useNavigate} from 'react-router-dom';
import {Route, Routes} from "react-router-dom";
import {MatchStats} from "../../stats/MatchStats";
import {navigate} from "react-big-calendar/lib/utils/constants";

export function Winner() {
    const info = useContext(Info);
    const navigate = useNavigate();
    if(info.isWinner)
        return <><p>YOU WON</p>
            <Button onClick={() => navigate(`/match/${info.code}/stats`)}
                    buttonType={ButtonType.primary}>TO STATS</Button></>
    else
        return <><p>YOU LOST</p>
            <Button onClick={() => navigate(`/match/${info.code}/stats`)}
                    buttonType={ButtonType.primary}>TO STATS</Button></>
}
