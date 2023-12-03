import DinNextSlabPro from "./DinNextSlabPro";

export function Subtitle({children, style}) {

    const textStyle = {
        fontSize: "16px",
        textTransform: "uppercase"
    }

    return (
        <DinNextSlabPro style={{...textStyle, ...style}}>
            {children}
        </DinNextSlabPro>
    );
}
