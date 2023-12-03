export default function DinNextSlabPro({style, children}) {

    const defaultStyle = {
        fontFamily: "DIN Next Slab Pro",
        margin: "-3px 0px"
    }

    return (
        <span style={{...defaultStyle, ...style}}>{children}</span>
    )
}
