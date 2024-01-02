import {Text} from "./Text";
import React from "react";
import {Pressable} from "./Pressable";

export function PressableText({children, onClick, style, underlined, color}) {
    const textStyle = {
        textDecoration: underlined ? "underline" : "inherit",
        color: color ? color : "inherit"
    }

    return (
        <Pressable onClick={onClick}>
            <Text style={{...textStyle, ...style}}>{children}</Text>
        </Pressable>
    )
}
