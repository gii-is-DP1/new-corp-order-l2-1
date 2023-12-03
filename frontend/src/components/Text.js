export function Text({children, style}) {

    const textStyle = {
        margin: 0,
        textTransform: "uppercase",
        fontFamily: "DIN Next Slab Pro"
    }

    return (
        <p style={{...textStyle, ...style}}>
            {children}
        </p>
    );
}
