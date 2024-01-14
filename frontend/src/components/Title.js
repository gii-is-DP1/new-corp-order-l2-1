import CoreMagic from "./CoreMagic";

export function Title({children, style}) {

    const textStyle = {
        fontSize: "22px",
        textTransform: "uppercase",
        lineHeight: 1.2
    }

    return (
        <CoreMagic style={{...textStyle, ...style}}>
            {children}
        </CoreMagic>
    );
}
