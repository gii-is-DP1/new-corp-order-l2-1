export default function BaseButton({children, onClick, buttonStyle, buttonContext}) {
    let backgroundColor = getBackgroundColor(buttonStyle, buttonContext);
    let textColor = getTextColor(backgroundColor);

    const style = {
        backgroundColor: backgroundColor,
        color: textColor,
        borderRadius:"0.4em",
        borderWidth:"0px",
        paddingTop:"3%",
        paddingBottom:"3%",
        paddingLeft:"20%",
        paddingRight:"20%",
    };

    return <button
        onClick={onClick}
        style={style}
    >{children}</button>
}
const successBackground = "#1A7B66";
const dangerBackground = "#D60000";
const orange = "#FC7349";
const white = "#F8F8F8";
const black = "#2C2C2C";

function getBackgroundColor(buttonStyle, context) {
    switch (buttonStyle) {
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
