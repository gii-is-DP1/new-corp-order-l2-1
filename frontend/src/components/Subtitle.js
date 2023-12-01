export function Subtitle({children, style}) {

    const textStyle = {
        margin: 0,
        fontSize: "18px",
        textTransform: "uppercase",
        fontFamily: "DIN Next Slab Pro"
    }

    return (
        <h2 style={{...textStyle, ...style}}>
            {children}
        </h2>
    );
}
