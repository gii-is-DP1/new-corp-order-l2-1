export default function CoreMagic({style, children}) {

    const defaultStyle = {
        fontFamily: "Core Magic",
       // letterSpacing: "-4px",
        margin: "-2px 5px -8px 0px"
    }

    return (
        <span style={{...defaultStyle, ...style}}>{children}</span>
    )
}
