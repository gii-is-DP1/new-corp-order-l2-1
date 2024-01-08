import {black, dangerBackground, orange, successBackground, white} from "../util/Colors";
import {Title} from "./Title";
import {useState} from "react";

export const ButtonType = {
    primary: orange,
    secondaryLight: black,
    secondaryDark: white,
    success: successBackground,
    danger: dangerBackground
}

export default function Button({onClick, buttonType, style, children, type, disabled}) {
    let textColor = getTextColor(buttonType);
    let [hover, setHover] = useState(false);
    let [click, setClick] = useState(false);

    const defaultStyle = {
        transition: "opacity 700ms, transform 100ms",
        backgroundColor: buttonType,
        opacity: hover ? 0.75 : disabled ? 0.30 : 1,
        transform: click ? "scale(0.95)" : "scale(1)",
        borderRadius: "8px",
        borderWidth: "0px",
        color: textColor,
        minWidth: "150px",
        minHeight: "45px",
        padding: "10px",
        display: "flex",
        flexDirection: "row",
        justifyContent: "center",
        alignItems: "center",
    };

    return (
        <button
            onClick={onClick}
            type={type}
            disabled={disabled}
            onMouseEnter={() => setHover(true)}
            onMouseLeave={() => setHover(false)}
            onMouseDown={() => setClick(true)}
            onMouseUp={() => setClick(false)}
            style={{...defaultStyle, ...style}}>
            <Title>{children}</Title>
        </button>
    )
}

function getTextColor(buttonType) {
    return buttonType === white ? black : white;
}
