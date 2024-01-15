import css from "./match.module.css";
import GoBackButton from "../components/GoBackButton";
import Chat from "./chat/chat";
import React from "react";

export function RightBar() {
    return <div className={css.rightBar}>
        <GoBackButton/>
        <Chat/>
    </div>
}
