import React from "react";

export function Pressable({children, onClick}) {
    const buttonStyle = {
        backgroundColor: "transparent",
        border: "none",
        cursor: "pointer"
    }

    return (
        <button onClick={onClick} style={buttonStyle}>
            {children}
        </button>
    )
}
