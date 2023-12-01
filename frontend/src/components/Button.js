import * as Colors from "../util/Colors";
import {black, dangerBackground, orange, successBackground, white} from "../util/Colors";
import {Title} from "./Title";

export default function Button({buttonText, onClick, buttonColor, buttonContext}) {
    let backgroundColor = getBackgroundColor(buttonColor, buttonContext);
    let textColor = getTextColor(backgroundColor);

    const style = {
        backgroundColor: backgroundColor,
        color: textColor,
        borderRadius: "8px",
        borderWidth: "0px",
        minWidth: "150px",
        maxHeight: "40px",
        padding: "10px",
        margin: "10px"
    };

    const textStyle = {
        fontSize: "20px",
        color: Colors.white,
        margin: "0px",
    }

    return (
        <button onClick={onClick} style={style}>
            <Title style={textStyle}>
                {buttonText}
            </Title>
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
