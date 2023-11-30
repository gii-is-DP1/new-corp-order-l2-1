import * as Colors from "../util/Colors";
import {black, dangerBackground, orange, successBackground, white} from "../util/Colors";

export default function Button({buttonText, onClick, buttonColor, buttonContext}) {
    let backgroundColor = getBackgroundColor(buttonColor, buttonContext);
    let textColor = getTextColor(backgroundColor);

    const style = {
        backgroundColor: backgroundColor,
        color: textColor,
        borderRadius: "8px",
        borderWidth: "0px",
        minWidth: "194px",
        maxHeight: "65px",
        padding: "15px 25px 15px",
        margin: "10px"
    };

    const textStyle = {
        fontSize: "24px",
        color: Colors.white,
        margin: "0px",

    }

    return (
        <button onClick={onClick} style={style}>
            <h1 style={textStyle}>
                {buttonText}
            </h1>
        </button>
    )
}


function getBackgroundColor(buttonColor, context) {
    switch (buttonColor) {
        case buttonStyles.success:
            return successBackground;
        case buttonStyles.danger:
            return dangerBackground;
        case buttonStyles.primary:
            if (context === buttonContexts.orange)
                return white;
            else return orange;
        case buttonStyles.secondary:
            if (context === buttonContexts.dark)
                return white;
            else
                return black;
    }
}

function getTextColor(backgroundColor) {
    if (backgroundColor === white)
        return black;
    else return white;
}

export const buttonStyles = {
    primary: 0,
    secondary: 1,
    success: 2,
    danger: 3
}
export const buttonContexts = {
    light: 0,
    dark: 1,
    orange: 2,
}

export class buttonContext {
}
