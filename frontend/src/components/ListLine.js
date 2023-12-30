import * as Colors from "../util/Colors";

export default function ListLine({iconSrc, children, sideContent}) {

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

    const iconStyle = {
        display: "flex",
        borderRadius: "50%",
        flexDirection: "column",
        alignItems: "center",
        justifyContent: "center",
        height: "30px",
        width: "30px",
    }

    const sideContentStyle = {
        display: "flex",
        flexDirection: "row",
        justifyContent: "flex-end",
        alignItems: "center",
        listStyleType: "none",
        margin: "0px",
    }

    return (
        <div style={containerStyle}>
            <div style={{...columnStyle, flex: 1}}>
                {iconSrc && <img src={iconSrc} alt={"icon"} style={iconStyle}/>}
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
