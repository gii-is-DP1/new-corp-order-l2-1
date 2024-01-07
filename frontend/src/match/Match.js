import {matchInfo} from "./data/MockupData";
import React from "react"
import css from "./match.module.css";
import {Main} from "./Main";
import {RightBar} from "./RightBar";

export const Info = React.createContext(matchInfo)

export default function Match() {
    return (
        <div className={css.match}>
            <Info.Provider value = {matchInfo} >
                <Main/>

            </Info.Provider>
        </div>)  /* <RightBar/>*/
}

