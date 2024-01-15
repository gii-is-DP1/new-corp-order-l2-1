import css from "./match.module.css";
import GoBackButton from "../components/GoBackButton";
import React from "react";
import {Chat} from "../components/Chat";

export function RightBar() {
    return <div className={css.rightBar}>
        <GoBackButton/>
        <Chat/>
    </div>
}
