import * as Colors from "../util/Colors";

export default function ListLine({children, sideContent, style}) {

    const containerStyle = {
        backgroundColor: Colors.white,
        color: Colors.black,
        display: "flex",
        flexDirection: "row",
        borderRadius: "8px",
        height: "50px",
    }

    const columnStyle = {
        display: "flex",
        flexDirection: "row",
        alignItems: "center",
        padding: "5px",
        gap: "5px"
    }

    return (
        <div style={{...containerStyle, ...style}}>
            <div style={{...columnStyle, flex: 1}}>
                {children}
            </div>

            {sideContent &&
                <div style={columnStyle}>
                    {sideContent}
                </div>
            }
        </div>
    )
}
