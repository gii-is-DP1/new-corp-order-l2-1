export function Subtitle({children, style}) {

    const textStyle = {
        fontSize: "16px",
        textTransform: "uppercase",
        fontFamily: "DIN Next Slab Pro",
        margin: "-3px 0px"
    }

    return (
        <h2 style={{...textStyle, ...style}}>
            {children}
        </h2>
    );
}
