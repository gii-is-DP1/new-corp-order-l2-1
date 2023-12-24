import {useState} from "react";
import css from "./lobby.css";

export function MatchCode({matchCode}) {
    const [isVisible, setIsVisible] = useState(true);
    const text = isVisible ? matchCode : "hidden code";
    const showHideIcon = isVisible
        ? <img src={"/svg/closed-eye.svg"} alt={"hide"}/>
        : <img src={"/svg/open-eye.svg"} alt={"show"}/>;
    const toggleVisibility = () => setIsVisible(!isVisible);

    return <i className={css.matchCode}>
        <span> {text} </span>
        <span onClick={toggleVisibility}>
            {showHideIcon}
        </span>
    </i>;
}
