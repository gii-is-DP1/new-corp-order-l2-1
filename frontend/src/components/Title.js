export function Title({children, style}) {

    const textStyle = {
        margin: 0,
        fontSize: "24px",
        textTransform: "uppercase",
        fontFamily: "Core Magic",
        letterSpacing: "-4px"
    }

    return (
        <h1 style={{...textStyle, ...style}}>
            {children}
        </h1>
    );
}
