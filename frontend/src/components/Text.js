import DinNextSlabPro from "./DinNextSlabPro";

export function Text({children, style}) {

    const textStyle = {
        margin: 0,
        textTransform: "uppercase"
    }

    return (
        <DinNextSlabPro style={{...textStyle, ...style}}>
            {children}
        </DinNextSlabPro>
    );
}
