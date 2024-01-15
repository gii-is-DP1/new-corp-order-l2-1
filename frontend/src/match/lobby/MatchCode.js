import {useContext, useState} from "react";
import css from "./lobby.module.css";
import {Info} from "../Match";

export function MatchCode({code}) {
    const [isVisible, setIsVisible] = useState(true);

    const text = isVisible ? code : "hidden code";
    const showHideIcon = isVisible
        ? <img src={"/svg/closed-eye.svg"} alt={"hide"}/>
        : <img src={"/svg/open-eye.svg"} alt={"show"}/>;
    const toggleVisibility = () => setIsVisible(!isVisible);

    return (
    <i className={css.matchCode}>
        <span title={"Copy"} style={{cursor: "pointer"}} onClick={() => navigator.clipboard.writeText(code)}> {text} </span>
        <span style={{cursor: "pointer"}} onClick={toggleVisibility}>
            {showHideIcon}
        </span>
    </i>)
}
