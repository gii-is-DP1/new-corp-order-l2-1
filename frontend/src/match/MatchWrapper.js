import Match from "./Match";
import {Chat} from "../components/Chat";
import {RightBar} from "./RightBar";
import css from "./match.module.css";


export default function MatchWrapper() {
    return <>
        <div className={css.match}>
            <Match/>
            <RightBar/>
        </div>
    </>
}
