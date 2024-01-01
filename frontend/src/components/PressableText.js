import {Text} from "./Text";
import React from "react";

export function PressableText({children, onClick, style, underlined, color}) {
    const textStyle = {
        textDecoration: underlined ? "underline" : "inherit",
        color: color ? color : "inherit"
    }

    const buttonStyle = {
        backgroundColor: "transparent",
        border: "none",
        cursor: "pointer"
    }

    return (
        <button onClick={onClick} style={buttonStyle}>
            <Text style={{...textStyle, ...style}}>{children}</Text>
        </button>
    )
}
