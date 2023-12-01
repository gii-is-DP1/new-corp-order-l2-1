export function Title({children, style}) {

    const textStyle = {
        fontSize: "22px",
        textTransform: "uppercase",
        fontFamily: "Core Magic",
        letterSpacing: "-4px",
        margin: "-2px 0px -8px 0px"
    }

    return (
        <h1 style={{...textStyle, ...style}}>
            {children}
        </h1>
    );
}
